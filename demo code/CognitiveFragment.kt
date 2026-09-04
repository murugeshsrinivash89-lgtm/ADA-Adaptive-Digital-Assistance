package com.ada.core

/**
 * Represents a small contextual unit used by ADA while constructing
 * an adaptive response.
 *
 * Cognitive fragments intentionally remain lightweight so that the
 * response engine can combine multiple pieces of context without
 * coupling them to a specific language model.
 */
data class CognitiveFragment(
    val type: FragmentType,
    val content: String,
    val priority: Int = 0,
    val source: String = "local"
) {
    fun isUsable(): Boolean = content.isNotBlank()

    fun normalized(): CognitiveFragment {
        return copy(content = content.trim())
    }
}

enum class FragmentType {
    LOGIC,
    MOTIVATION,
    TIME_AWARE,
    CONTEXT,
    SAFETY,
    PERSONALIZATION
}

class CognitiveFragmentBuilder {

    private val fragments = mutableListOf<CognitiveFragment>()

    fun add(
        type: FragmentType,
        content: String,
        priority: Int = 0,
        source: String = "local"
    ): CognitiveFragmentBuilder {
        if (content.isNotBlank()) {
            fragments += CognitiveFragment(type, content, priority, source)
        }
        return this
    }

    fun build(): List<CognitiveFragment> {
        return fragments
            .map { it.normalized() }
            .filter { it.isUsable() }
            .sortedByDescending { it.priority }
    }
}
