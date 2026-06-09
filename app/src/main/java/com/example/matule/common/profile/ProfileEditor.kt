package com.example.matule.common.profile

import com.example.matule.common.validation.EmailValidator
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
        return when {
            profile.name.isBlank() -> Sprint2ValidationResult.Error("Введите имя")
            !EmailValidator().isValid(profile.email) -> Sprint2ValidationResult.Error("Некорректный email")
            !isValidPhone(profile.phone) -> Sprint2ValidationResult.Error("Некорректный телефон")
            else -> Sprint2ValidationResult.Success
        }
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
        return profile.copy(name = name, email = email, phone = phone)
    }

    private fun isValidPhone(phone: String): Boolean {
        val clearPhone = phone.trim()
        return clearPhone.isNotEmpty() && clearPhone.all { it.isDigit() || it == '+' }
    }
}
