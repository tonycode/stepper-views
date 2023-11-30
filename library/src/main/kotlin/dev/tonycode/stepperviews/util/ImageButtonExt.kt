package dev.tonycode.stepperviews.util

import android.widget.ImageButton


internal fun ImageButton.enable() {
    isEnabled = true
    imageAlpha = 0xFF
}

internal fun ImageButton.disable() {
    isEnabled = false
    imageAlpha = 0x3F
}
