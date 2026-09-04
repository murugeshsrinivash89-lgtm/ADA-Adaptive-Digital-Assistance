package com.ada.core

import java.time.LocalTime

/**
 * Central orchestration layer for ADA.
 *
 * This demo implementation is intentionally deterministic and local.
 * A production version can connect this orchestration layer to an
 * on-device SLM without exposing private user data unnecessarily.
 */
class AdaNeuralCore(
    private val bondManager: BondManager = BondManager(),
    private val clock: () -> LocalTime = { LocalTime.now() }
) {

    private val responseHistory = ArrayDeque<String>()
    private val maxHistory = 20

    fun process(input: String): String {
        val command = input.trim()

        if (command.isEmpty()) {
            return "Please provide a command."
        }

        bondManager.registerInteraction()

        val fragments = CognitiveFragmentBuilder()
            .add(FragmentType.CONTEXT, buildContextFragment())
            .add(FragmentType.PERSONALIZATION, buildPersonalizationFragment(), 1)
            .add(FragmentType.LOGIC, buildLogicFragment(command), 2)
            .build()

        val response = composeResponse(command, fragments)
        remember(response)

        return response
    }

    private fun buildContextFragment(): String {
        val hour = clock().hour

        return when (hour) {
            in 5..11 -> "Current context: morning."
            in 12..16 -> "Current context: afternoon."
            in 17..21 -> "Current context: evening."
            else -> "Current context: night."
        }
    }

    private fun buildPersonalizationFragment(): String {
        return "Interaction profile: Bond Level ${bondManager.level()}."
    }

    private fun buildLogicFragment(command: String): String {
        return when {
            command.equals("hello", true) ->
                "Greeting request detected."

            command.contains("status", true) ->
                "System status request detected."

            command.contains("bond", true) ->
                "Personalization status request detected."

            command.contains("help", true) ->
                "Help request detected."

            else ->
                "General command detected."
        }
    }

    private fun composeResponse(
        command: String,
        fragments: List<CognitiveFragment>
    ): String {

        return when {
            command.equals("hello", true) ->
                "Hello, Architect. ADA is ready. Bond Level: ${bondManager.level()}."

            command.contains("status", true) ->
                "ADA core is operational. Local response engine is active. Bond Level: ${bondManager.level()}."

            command.contains("bond", true) ->
                "Current Bond Level: ${bondManager.level()}. " +
                    "Interactions recorded: ${bondManager.interactionCount()}."

            command.contains("help", true) ->
                "Available demo commands: hello, status, bond, help."

            else -> {
                val context = fragments
                    .firstOrNull { it.type == FragmentType.CONTEXT }
                    ?.content
                    ?: "Context unavailable."

                "Command received: \"$command\". $context " +
                    "ADA has processed the request locally."
            }
        }
    }

    private fun remember(response: String) {
        responseHistory.addLast(response)

        while (responseHistory.size > maxHistory) {
            responseHistory.removeFirst()
        }
    }

    fun recentResponses(): List<String> = responseHistory.toList()

    fun clearLocalContext() {
        responseHistory.clear()
    }
}
