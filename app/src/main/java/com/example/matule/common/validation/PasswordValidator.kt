package com.example.matule.common.validation

/**
 * Purpose: Validates password complexity rules for Sprint 2.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class PasswordValidator {

    /**
     * Purpose: Checks length, uppercase, lowercase, digit, and special character.
     */
    fun isValid(password: String): Boolean {
        return password.length >= MIN_LENGTH &&
            password.any { it in 'A'..'Z' } &&
            password.any { it in 'a'..'z' } &&
            password.any { it.isDigit() } &&
            password.any { !it.isLetterOrDigit() }
    }

    private companion object {
        const val MIN_LENGTH = 8
    }
}
