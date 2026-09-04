package com.ada.architect

/**
 * Local usage-history predictor.
 *
 * This demo uses frequency as a baseline. A production implementation
 * could replace this with a privacy-preserving temporal model.
 */
class PredictiveEngine {

    fun predictNextApplication(history: List<String>): String? {
        return history
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .groupingBy { it }
            .eachCount()
            .maxByOrNull { it.value }
            ?.key
    }

    fun rankApplications(history: List<String>): List<AppPrediction> {
        return history
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .groupingBy { it }
            .eachCount()
            .entries
            .sortedByDescending { it.value }
            .map {
                AppPrediction(
                    application = it.key,
                    score = it.value
                )
            }
    }
}

data class AppPrediction(
    val application: String,
    val score: Int
)
