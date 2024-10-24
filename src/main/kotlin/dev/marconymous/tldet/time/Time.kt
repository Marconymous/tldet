package dev.marconymous.tldet.time

import kotlin.concurrent.thread

class Time(private val millis: Long) {
    operator fun get(unit: TimeUnit): Long = millis / unit.millis

    operator fun plus(time: Time): Time = Time(millis + time.millis)
    operator fun plus(time: String): Time = this + fromString(time)

    operator fun minus(time: Time): Time = Time(millis - time.millis)
    operator fun minus(time: String): Time = this - fromString(time)

    operator fun times(scalar: Long): Time = Time(millis * scalar)
    operator fun times(scalar: Double): Time = Time((millis * scalar).toLong())

    operator fun div(scalar: Long): Time =  Time(millis / scalar)
    operator fun div(scalar: Double): Time = Time((millis / scalar).toLong())

    fun execute(callback: () -> Unit, async: Boolean = false) {
        val waitTime = millis - System.currentTimeMillis()
        if (waitTime < 0) throw IllegalArgumentException("Cannot travel back in time :(")

        if (async) {
            thread {
                Thread.sleep(waitTime)
                callback()
            }
            return
        }

        Thread.sleep(waitTime)
        callback()
    }

    companion object {
        fun now(): Time {
            return Time(System.currentTimeMillis())
        }

        operator fun plus(additional: String): Time {
            return now() + additional
        }

        fun fromString(time: String): Time {
            val regex = Regex("([0-9]+)([a-zA-Z]+)")
            val matches = regex.findAll(time)

            var totalMillis = 0L

            for (match in matches) {
                val value = match.groupValues[1].toLong()
                val unit = TimeUnit.entries.find { it.short == match.groupValues[2] }
                    ?: throw IllegalArgumentException("Invalid time unit: ${match.groupValues[2]}")
                totalMillis += value * unit.millis
            }

            if (totalMillis == 0L) throw IllegalArgumentException("Invalid time format")

            return Time(totalMillis)
        }
    }
}