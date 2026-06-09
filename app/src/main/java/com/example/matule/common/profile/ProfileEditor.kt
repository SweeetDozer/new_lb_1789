package com.example.matule.common.profile

import com.example.matule.common.validation.EmailValidator
import com.example.matule.common.validation.Sprint2ValidationResult

/**
 * Purpose: Validates and saves edited profile fields.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class ProfileEditor {
    private val emailValidator = EmailValidator()

    /**
     * Purpose: Saves profile only when edited fields are valid.
     */
    fun save(profile: Profile): Sprint2ValidationResult {
        return when {
            !validateName(profile.name) -> Sprint2ValidationResult.Error(NAME_ERROR)
            !validateEmail(profile.email) -> Sprint2ValidationResult.Error(EMAIL_ERROR)
            !validatePhone(profile.phone) -> Sprint2ValidationResult.Error(PHONE_ERROR)
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

    private fun validateName(name: String): Boolean {
        return name.isNotBlank()
    }

    private fun validateEmail(email: String): Boolean {
        return emailValidator.isValid(email)
    }

    private fun validatePhone(phone: String): Boolean {
        val clearPhone = phone.trim()
        return clearPhone.isNotEmpty() && clearPhone.all { it.isDigit() || it == '+' }
    }

    private companion object {
        const val NAME_ERROR = "Введите имя"
        const val EMAIL_ERROR = "Некорректный email"
        const val PHONE_ERROR = "Некорректный телефон"
    }
}
