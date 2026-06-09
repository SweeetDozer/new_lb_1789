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
        get() = true

    val orientation: QrOrientation
        get() = QrOrientation.VERTICAL

    val brightnessPercent: Int
        get() = 75
}

/**
 * Purpose: Lists QR screen orientations.
 */
enum class QrOrientation {
    VERTICAL,
    HORIZONTAL
}
