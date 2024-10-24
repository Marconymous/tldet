package dev.marconymous.tldet.`fun`


class Chance<T>(val weight: Double, val it: T) {
    companion object {
        fun <T> roll(chances: List<Chance<T>>): T {
            val totalWeight = chances.sumOf { it.weight }
            var random = Math.random() * totalWeight
            for (chance in chances) {
                random -= chance.weight
                if (random <= 0) {
                    return chance.it
                }
            }
            return chances.last().it
        }

        fun <T> rollMultiple(chances: List<Chance<T>>, times: Int) = (0..<times).map { roll(chances) }

        fun <T> from(map: Map<T, Double>): List<Chance<T>> = map.map { Chance(it.value, it.key) }

        fun <T> from(vararg pairs: Pair<T, Double>): List<Chance<T>> = from(pairs.toMap())
    }
}