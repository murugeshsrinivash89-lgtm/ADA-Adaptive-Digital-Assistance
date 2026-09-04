package com.ada.guardian

/**
 * Experimental interaction-intensity analyzer.
 *
 * Touch intensity is treated only as an interaction signal here.
 * It must not be presented as a clinical stress measurement.
 */
class NeuralSync(
    private val highThreshold: Double = 0.80,
    private val moderateThreshold: Double = 0.50
) {

    fun analyzeTouchIntensity(samples: List<Float>): NeuralSyncResult {
        if (samples.isEmpty()) {
            return NeuralSyncResult(
                level = InteractionLevel.UNKNOWN,
                message = "Insufficient interaction data.",
                averageIntensity = null
            )
        }

        val average = samples
            .map { it.coerceIn(0f, 1f) }
            .average()

        return when {
            average > highThreshold ->
                NeuralSyncResult(
                    InteractionLevel.HIGH,
                    "High interaction intensity detected.",
                    average
                )

            average > moderateThreshold ->
                NeuralSyncResult(
                    InteractionLevel.MODERATE,
                    "Moderate interaction intensity detected.",
                    average
                )

            else ->
                NeuralSyncResult(
                    InteractionLevel.LOW,
                    "Low interaction intensity detected.",
                    average
                )
        }
    }
}

data class NeuralSyncResult(
    val level: InteractionLevel,
    val message: String,
    val averageIntensity: Double?
)

enum class InteractionLevel {
    LOW,
    MODERATE,
    HIGH,
    UNKNOWN
}
