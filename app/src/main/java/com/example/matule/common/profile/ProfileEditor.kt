package com.example.matule.common.profile

import com.example.matule.common.validation.Sprint2ValidationResult

/**
 * Purpose: Validates and saves edited profile fields.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class ProfileEditor {

    /**
     * Purpose: Saves profile only when edited fields are valid.
     */
    fun save(profile: Profile): Sprint2ValidationResult {
        TODO("Sprint 5 GREEN")
    }

    /**
     * Purpose: Creates updated profile copy with changed fields.
     */
    fun edit(
        profile: Profile,
        name: String = profile.name,
        email: String = profile.email,
        phone: String = profile.phone
    ): Profile {
        TODO("Sprint 5 GREEN")
    }
}

