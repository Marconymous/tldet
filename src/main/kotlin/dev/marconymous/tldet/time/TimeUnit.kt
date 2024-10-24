package dev.marconymous.tldet.time

enum class TimeUnit(val millis: Long, val short: String) {
    MILLISECOND(1, "ms"),
    SECOND(1000L, "s"),
    MINUTE(60 * SECOND.millis, "m"),
    HOUR(60 * MINUTE.millis, "h"),
    DAY(24 * HOUR.millis, "d"),
    WEEK(7 * DAY.millis, "w"),
    MONTH(30 * DAY.millis, "M"),
    YEAR(12 * MONTH.millis, "y")
}