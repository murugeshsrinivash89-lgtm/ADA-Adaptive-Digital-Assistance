package com.ada.privacy

/**
 * Demonstration detector for common sensitive-data patterns.
 *
 * This is a heuristic layer, not a cryptographic security mechanism.
 * Production implementations should use carefully tested classifiers
 * and should minimize the amount of sensitive information retained.
 */
class SensitiveDataDetector {

    private val keywordPattern = Regex(
        pattern = """(?i)\b(password|passwd|pin|otp|passcode|secret)\b"""
    )

    private val numericPattern = Regex(
        pattern = """\b\d{4,8}\b"""
    )

    fun containsSensitiveContent(text: String): Boolean {
        return keywordPattern.containsMatchIn(text) ||
            numericPattern.containsMatchIn(text)
    }

    fun detect(text: String): List<SensitiveMatch> {
        val results = mutableListOf<SensitiveMatch>()

        keywordPattern.findAll(text).forEach {
            results += SensitiveMatch(
                type = SensitiveType.SECURITY_KEYWORD,
                start = it.range.first,
                end = it.range.last
            )
        }

        numericPattern.findAll(text).forEach {
            results += SensitiveMatch(
                type = SensitiveType.POSSIBLE_SECRET_NUMBER,
                start = it.range.first,
                end = it.range.last
            )
        }

        return results.sortedBy { it.start }
    }

    fun mask(text: String): String {
        if (text.isEmpty()) return text

        val ranges = detect(text)
            .map { it.start..it.end }
            .sortedByDescending { it.first }

        var result = text

        for (range in ranges) {
            val replacement = "*".repeat(range.last - range.first + 1)
            result = result.replaceRange(range, replacement)
        }

        return result
    }
}

data class SensitiveMatch(
    val type: SensitiveType,
    val start: Int,
    val end: Int
)

enum class SensitiveType {
    SECURITY_KEYWORD,
    POSSIBLE_SECRET_NUMBER
}
