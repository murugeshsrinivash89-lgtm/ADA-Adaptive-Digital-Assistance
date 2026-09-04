package com.ada.architect

/**
 * Action abstraction for future Android accessibility integration.
 *
 * The demo only validates actions. It does not silently control
 * third-party applications.
 */
class UniversalController {

    private val supportedActions = setOf(
        "OPEN_APP",
        "CLICK",
        "TYPE_TEXT",
        "SCROLL",
        "BACK"
    )

    fun validateAction(action: String): Boolean {
        return action.trim().isNotEmpty()
    }

    fun isSupported(action: String): Boolean {
        return action.trim().uppercase() in supportedActions
    }

    fun createRequest(action: String, target: String? = null): ControllerRequest? {
        val normalized = action.trim().uppercase()

        if (!isSupported(normalized)) return null

        return ControllerRequest(
            action = normalized,
            target = target?.trim()
        )
    }
}

data class ControllerRequest(
    val action: String,
    val target: String?
)
