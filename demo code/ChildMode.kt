package com.ada.guardian

/**
 * Simple local policy engine for the Child Mode prototype.
 */
class ChildMode {

    private var enabled = false

    private val restrictedCategories = setOf(
        "unsafe",
        "restricted",
        "adult"
    )

    fun setEnabled(value: Boolean) {
        enabled = value
    }

    fun isEnabled(): Boolean = enabled

    fun allowCategory(category: String): Boolean {
        if (!enabled) return true

        return category.trim().lowercase() !in restrictedCategories
    }

    fun filterCategories(categories: List<String>): List<String> {
        return categories.filter { allowCategory(it) }
    }
}
