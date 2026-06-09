package com.example.matule.presentation.profile

import com.example.matule.common.profile.Profile
import com.example.matule.common.profile.ProfilePhotoSource

/**
 * Purpose: Stores mock profile data for Sprint 5 profile UI.
 * Creation date: 2026-06-09
 * Author: Mors
 */
object ProfileUiState {
    var profile: Profile = Profile(
        name = "Mors",
        email = "test123@mail.com",
        phone = "+79990000000",
        avatarSource = ProfilePhotoSource.GALLERY,
        loyaltyQrData = "MATULE-LOYALTY-0001"
    )
}

