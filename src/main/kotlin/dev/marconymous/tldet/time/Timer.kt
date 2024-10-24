package dev.marconymous.tldet.time

class Timer(start: Boolean = false) {
    init {
        if (start) start()
    }

    private var startTime: Long = 0
    private var timeElapsed: Long = 0

    fun start(): Long {
        startTime = System.currentTimeMillis()
        return startTime
    }

    fun stop(): Long {
        val currentTime = System.currentTimeMillis()
        val currentElapsed = (currentTime - startTime)
        timeElapsed += currentElapsed

        return currentElapsed
    }

    fun lap(): Long {
        val elapsed = stop()
        start()
        return elapsed
    }

    fun reset() {
        timeElapsed = 0
    }

    companion object {
        fun <T> timeExecution(func: () -> T): Pair<T, Long> {
            val timer = Timer(true)
            val returnVal = func()
            return Pair(returnVal, timer.stop())
        }
    }
}