package com.ada.security

/**
 * State machine for ADA's protected-interface concept.
 *
 * This class does not claim to detect device theft or physical
 * grabbing. It provides the state abstraction required by a future
 * sensor/authentication implementation.
 */
class GhostMode {

    private var active = false

    fun activate() {
        active = true
    }

    fun deactivateAfterAuthentication(authenticated: Boolean): Boolean {
        if (!authenticated) return false

        active = false
        return true
    }

    fun toggle() {
        active = !active
    }

    fun isActive(): Boolean = active
}
