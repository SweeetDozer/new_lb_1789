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
        return PasswordRules.hasMinLength(password) &&
            PasswordRules.hasUppercase(password) &&
            PasswordRules.hasLowercase(password) &&
            PasswordRules.hasDigit(password) &&
            PasswordRules.hasSpecialCharacter(password)
    }
}
