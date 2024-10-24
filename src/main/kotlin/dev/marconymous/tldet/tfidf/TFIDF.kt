package dev.marconymous.tldet.tfidf

import kotlin.math.log10

class TFIDF<T> private constructor(private val tfIdfData: Map<List<T>, Map<T, Double>>) {
    fun findBestDocument(vararg terms: T): List<T> {
        val docMatchData = tfIdfData.map { (doc, termData) ->
            Pair(doc, terms.sumOf { term ->
                termData[term] ?: 0.0
            })
        }.toMap()

        return docMatchData.maxBy { it.value }.key
    }

    companion object {
        fun <T> of(documents: List<List<T>>): TFIDF<T> {
            val tf = termFrequencies(documents)
            val df = documentFrequencies(documents)
            val idf = idf(df)
            val tfidf = tfidf(documents, tf, idf)
            return TFIDF(tfidf)
        }

        private fun <T> tfidf(
            docs: List<List<T>>,
            termFreqs: Map<List<T>, FrequencyMap<T>>,
            inverseDocFreqs: Map<T, Double>
        ): Map<List<T>, Map<T, Double>> {
            return docs.associateWith { doc ->
                val tfs =
                    termFreqs[doc] ?: throw IllegalArgumentException("There's an issue with the provided documents")
                val tfIdf = tfs.map { (term, freq) ->
                    val idf = inverseDocFreqs[term]
                        ?: throw IllegalArgumentException("There's an issue with the provided documents")
                    Pair(term, freq * idf)
                }.toMap()
                tfIdf
            }
        }

        private fun <T> idf(df: FrequencyMap<T>): Map<T, Double> {
            return df.map { (term, freq) ->
                val idf = log10(df.size.toDouble() / (1 + freq))
                Pair(term, idf)
            }.toMap()
        }

        private fun <T> documentFrequencies(documents: List<List<T>>): FrequencyMap<T> {
            val dfs = FrequencyMap<T>()
            documents.forEach { doc ->
                doc.distinct().forEach(dfs::inc)
            }
            return dfs
        }

        private fun <T> termFrequencies(documents: List<List<T>>): Map<List<T>, FrequencyMap<T>> {
            return documents.associateWith { doc ->
                val tfd = FrequencyMap<T>()
                doc.forEach(tfd::inc)
                tfd
            }
        }
    }
}

private class FrequencyMap<T>() : HashMap<T, Int>() {
    fun inc(item: T, defaultValue: Int = 0) {
        val current = getOrElse(item) { defaultValue }
        this[item] = current + 1
    }
}