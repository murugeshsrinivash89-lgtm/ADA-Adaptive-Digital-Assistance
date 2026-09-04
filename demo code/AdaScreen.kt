package com.ada.ui

import android.app.Activity
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import com.ada.core.AdaNeuralCore
import com.ada.privacy.PrivacyGuard

/**
 * Lightweight programmatic UI for the ADA demonstration build.
 */
class AdaScreen(
    private val activity: Activity,
    private val neuralCore: AdaNeuralCore,
    private val privacyGuard: PrivacyGuard = PrivacyGuard()
) {

    fun build(): View {
        val layout = LinearLayout(activity).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 60, 40, 40)
            setBackgroundColor(Color.BLACK)
        }

        val title = TextView(activity).apply {
            text = "ADA"
            textSize = 28f
            setTextColor(Color.WHITE)
            gravity = Gravity.CENTER
        }

        val subtitle = TextView(activity).apply {
            text = "Adaptive Digital Assistant"
            textSize = 14f
            setTextColor(Color.LTGRAY)
            gravity = Gravity.CENTER
        }

        val input = EditText(activity).apply {
            hint = "Enter a command"
            setTextColor(Color.WHITE)
            setHintTextColor(Color.GRAY)
            setSingleLine(false)
        }

        val output = TextView(activity).apply {
            text = "ADA is ready."
            textSize = 16f
            setTextColor(Color.WHITE)
            setPadding(0, 30, 0, 30)
        }

        val processButton = Button(activity).apply {
            text = "PROCESS"
            setOnClickListener {
                val rawInput = input.text.toString()
                val sanitized = privacyGuard.sanitize(rawInput)

                val privacyNote = if (sanitized.containedSensitiveData) {
                    "Sensitive content was masked before processing. "
                } else {
                    ""
                }

                output.text =
                    privacyNote + neuralCore.process(sanitized.text)
            }
        }

        layout.addView(title)
        layout.addView(subtitle)
        layout.addView(input)
        layout.addView(processButton)
        layout.addView(output)

        return layout
    }
}
