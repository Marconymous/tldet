package dev.marconymous.tldet.tfidf

import dev.marconymous.tldet.time.Timer
import dev.marconymous.tldet.time.Timer.Companion.timeExecution
import org.jsoup.Jsoup
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.File

class TFIDFTest {

    @Test
    fun testTFIDF() {
        val folder = File("./src/test/resources/tfidf/gl4")
        val documents = folder.listFiles()!!.map { file ->
            Jsoup.parse(file.readText()).text().lowercase().split("[,./\\s]".toRegex())
        }


        val (tfidf, calcTime) = timeExecution {
             TFIDF.of(documents)
        }
        println("CalcTime $calcTime")

        val (result, searchTime) = timeExecution {
            tfidf.findBestDocument("fill", "polygon")
        }
        println("Searchtime $searchTime")
        println(result)
    }
}