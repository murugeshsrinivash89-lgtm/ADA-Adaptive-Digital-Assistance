package com.ada.core

/**
 * Lightweight local interaction tracker.
 *
 * Bond Level is a personalization signal used to change interaction
 * style. It is not intended to represent emotion, consciousness,
 * psychological attachment, or a clinical measurement.
 */
class BondManager(
    private val maximumLevel: Int = 10,
    private val interactionsPerLevel: Int = 10
) {

    private var interactionCount: Int = 0

    fun registerInteraction() {
        interactionCount++
    }

    fun registerInteractions(count: Int) {
        if (count > 0) {
            interactionCount += count
        }
    }

    fun level(): Int {
        if (interactionsPerLevel <= 0) return 0
        return (interactionCount / interactionsPerLevel)
            .coerceIn(0, maximumLevel)
    }

    fun interactionCount(): Int = interactionCount

    fun reset() {
        interactionCount = 0
    }

    fun progressToNextLevel(): Int {
        if (level() >= maximumLevel) return 0
        return interactionsPerLevel -
            (interactionCount % interactionsPerLevel)
    }
}
