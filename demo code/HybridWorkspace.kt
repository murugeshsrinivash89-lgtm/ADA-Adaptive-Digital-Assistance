package com.ada.architect

/**
 * Research workspace abstraction.
 *
 * The demo performs only local text summarization. Network retrieval
 * should be implemented separately and should never require embedding
 * personal credentials or private network addresses in source code.
 */
class HybridWorkspace {

    fun createLocalSummary(
        query: String,
        sourceText: String,
        maximumCharacters: Int = 800
    ): ResearchResult {

        val normalized = sourceText
            .replace(Regex("\\s+"), " ")
            .trim()

        return ResearchResult(
            query = query.trim(),
            summary = normalized.take(maximumCharacters),
            processedLocally = true,
            sourceType = "local-demo"
        )
    }

    fun isReady(): Boolean = true
}

data class ResearchResult(
    val query: String,
    val summary: String,
    val processedLocally: Boolean,
    val sourceType: String
)
