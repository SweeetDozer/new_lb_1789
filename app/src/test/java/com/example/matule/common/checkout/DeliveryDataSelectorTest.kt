package com.example.matule.common.checkout

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Purpose: Tests Sprint 4 delivery source selection.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class DeliveryDataSelectorTest {

    @Test
    fun selectingProfileReturnsProfileAddress() {
        val result = DeliveryDataSelector().select(
            source = DeliveryDataSource.PROFILE,
            profileAddress = "Profile address",
            geolocationAddress = "Geo address"
        )

        assertEquals("Profile address", result.address)
        assertEquals(DeliveryDataSource.PROFILE, result.source)
    }

    @Test
    fun selectingGeolocationReturnsGeolocationAddress() {
        val result = DeliveryDataSelector().select(
            source = DeliveryDataSource.GEOLOCATION,
            profileAddress = "Profile address",
            geolocationAddress = "Geo address"
        )

        assertEquals("Geo address", result.address)
        assertEquals(DeliveryDataSource.GEOLOCATION, result.source)
    }
}
