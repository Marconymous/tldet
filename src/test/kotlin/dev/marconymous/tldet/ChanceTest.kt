package dev.marconymous.tldet

import dev.marconymous.tldet.`fun`.Chance
import org.junit.jupiter.api.Test

class ChanceTest {
    @Test
    fun testRoll() {
        val chances = Chance.from(
            "a" to 0.5,
            "b" to 0.5
        )
        val rolls = mutableMapOf<String, Int>()
        (0..99).map {
            Chance.roll(chances)
        }.forEach {
            rolls[it] = rolls.getOrDefault(it, 0) + 1
        }

        println(rolls)
    }
}