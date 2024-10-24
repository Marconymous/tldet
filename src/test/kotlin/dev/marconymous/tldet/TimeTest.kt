package dev.marconymous.tldet

import dev.marconymous.tldet.time.Time
import dev.marconymous.tldet.time.TimeUnit.*
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class TimeTest {

    @Test
    fun getSeconds() {
        val time = Time(1000)
        assertEquals(1, time[SECOND])
    }

    @Test
    fun getMinutes() {
        val time = Time(60 * 1000)
        assertEquals(1, time[MINUTE])
    }

    @Test
    fun getHours() {
        val time = Time(60 * 60 * 1000)
        assertEquals(1, time[HOUR])
    }

    @Test
    fun plus() {
        val time = Time(1000) + Time(1000)
        assertEquals(2000L, time[MILLISECOND])
    }

    @Test
    fun plusString() {
        val time = Time(1000) + "1m1s"
        assertEquals(62000L, time[MILLISECOND])
    }

    @Test
    fun execute() {
        val before = System.currentTimeMillis()
        var executedAt = 0L
        (Time.now() + "1s").execute({
            executedAt = System.currentTimeMillis()
        })
        assertTrue(executedAt - before >= 1000)
    }

    @Test
    fun executeAsync() {
        val before = System.currentTimeMillis()
        (Time.now() + "1s").execute({
            println("Hello, World!")
        }, async = true)
        val after = System.currentTimeMillis()
        assertTrue(after - before < 1000)
    }
}