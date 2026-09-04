package com.ada.config

/**
 * Safe configuration holder.
 *
 * No personal IP addresses, credentials, API keys, tokens, or device
 * identifiers belong in this class. Environment-specific values should
 * be supplied through local configuration or a secure secret mechanism.
 */
object SafeConfig {

    const val APP_NAME = "ADA"
    const val DEMO_MODE = true

    // Placeholder only. Replace locally when a real endpoint is required.
    const val API_BASE_URL_PLACEHOLDER = "https://example.invalid/api"

    fun validate(): Boolean {
        return APP_NAME.isNotBlank() && DEMO_MODE
    }
}
