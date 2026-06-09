package com.example.matule.common.profile

/**
 * Purpose: Stores fullscreen loyalty QR state.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class LoyaltyQrState(
    val loyaltyData: String
) {
    val isFullscreen: Boolean
        get() = IS_FULLSCREEN

    val orientation: QrOrientation
        get() = QR_ORIENTATION

    val brightnessPercent: Int
        get() = QR_BRIGHTNESS_PERCENT

    private companion object {
        const val IS_FULLSCREEN = true
        const val QR_BRIGHTNESS_PERCENT = 75
        val QR_ORIENTATION = QrOrientation.VERTICAL
    }
}

/**
 * Purpose: Lists QR screen orientations.
 */
enum class QrOrientation {
    VERTICAL,
    HORIZONTAL
}
