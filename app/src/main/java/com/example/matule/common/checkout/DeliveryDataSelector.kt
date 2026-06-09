package com.example.matule.common.checkout

/**
 * Purpose: Chooses delivery address from profile or mock geolocation data.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class DeliveryDataSelector {

    /**
     * Purpose: Returns selected delivery address and source.
     */
    fun select(source: DeliveryDataSource, profileAddress: String, geolocationAddress: String): DeliverySelection {
        return DeliverySelection(address = addressFor(source, profileAddress, geolocationAddress), source = source)
    }

    private fun addressFor(
        source: DeliveryDataSource,
        profileAddress: String,
        geolocationAddress: String
    ): String {
        return when (source) {
            DeliveryDataSource.PROFILE -> profileAddress
            DeliveryDataSource.GEOLOCATION -> geolocationAddress
        }
    }
}

/**
 * Purpose: Stores selected delivery result.
 * Creation date: 2026-06-09
 * Author: Mors
 */
data class DeliverySelection(
    val address: String,
    val source: DeliveryDataSource
)
