package com.example.matule.common.profile

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Purpose: Tests Sprint 5 loyalty QR state.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class LoyaltyQrStateTest {

    @Test
    fun qrOpensFullscreen() {
        assertTrue(LoyaltyQrState("MATULE-123").isFullscreen)
    }

    @Test
    fun qrUsesVerticalOrientation() {
        assertEquals(QrOrientation.VERTICAL, LoyaltyQrState("MATULE-123").orientation)
    }

    @Test
    fun qrBrightnessTargetIs75() {
        assertEquals(75, LoyaltyQrState("MATULE-123").brightnessPercent)
    }

    @Test
    fun qrStoresLoyaltyData() {
        assertEquals("MATULE-123", LoyaltyQrState("MATULE-123").loyaltyData)
    }
}

