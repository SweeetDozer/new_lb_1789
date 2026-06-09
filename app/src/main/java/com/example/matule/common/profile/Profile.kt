package com.example.matule.common.profile

/**
 * Purpose: Stores user profile data for Sprint 5 profile logic.
 * Creation date: 2026-06-09
 * Author: Mors
 */
data class Profile(
    val name: String,
    val email: String,
    val phone: String,
    val avatarSource: ProfilePhotoSource,
    val loyaltyQrData: String
)

