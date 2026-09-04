package com.ada.privacy

/**
 * Clears temporary application-level buffers.
 *
 * Note: application code cannot guarantee physical RAM erasure in the
 * same way a secure hardware primitive might. This class therefore
 * represents best-effort logical clearing for demo purposes.
 */
class MemoryFlush {

    fun clearStrings(buffer: MutableList<String>) {
        buffer.clear()
    }

    fun clearBytes(buffer: ByteArray) {
        buffer.fill(0)
    }

    fun clearChars(buffer: CharArray) {
        buffer.fill('\u0000')
    }

    fun clearMap(buffer: MutableMap<*, *>) {
        buffer.clear()
    }
}
