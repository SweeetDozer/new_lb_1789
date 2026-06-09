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
        get() = TODO("Sprint 5 GREEN")

    val orientation: QrOrientation
        get() = TODO("Sprint 5 GREEN")

    val brightnessPercent: Int
        get() = TODO("Sprint 5 GREEN")
}

/**
 * Purpose: Lists QR screen orientations.
 */
enum class QrOrientation {
    VERTICAL,
    HORIZONTAL
}

