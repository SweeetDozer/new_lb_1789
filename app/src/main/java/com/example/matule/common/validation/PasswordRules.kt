package com.example.matule.common.validation

/**
 * Purpose: Keeps common password requirement checks for Sprint 2 validators.
 * Creation date: 2026-06-07
 * Author: Mors
 */
object PasswordRules {
    const val MIN_LENGTH = 8

    /**
     * Purpose: Checks that password has enough symbols.
     */
    fun hasMinLength(password: String): Boolean {
        return password.length >= MIN_LENGTH
    }

    /**
     * Purpose: Checks that password contains uppercase latin letter.
     */
    fun hasUppercase(password: String): Boolean {
        return password.any { it in 'A'..'Z' }
    }

    /**
     * Purpose: Checks that password contains lowercase latin letter.
     */
    fun hasLowercase(password: String): Boolean {
        return password.any { it in 'a'..'z' }
    }

    /**
     * Purpose: Checks that password contains digit.
     */
    fun hasDigit(password: String): Boolean {
        return password.any { it.isDigit() }
    }

    /**
     * Purpose: Checks that password contains special character.
     */
    fun hasSpecialCharacter(password: String): Boolean {
        return password.any { !it.isLetterOrDigit() }
    }

    /**
     * Purpose: Counts how many password character groups are present.
     */
    fun matchedGroupsCount(password: String): Int {
        return listOf(
            hasUppercase(password),
            hasLowercase(password),
            hasDigit(password),
            hasSpecialCharacter(password)
        ).count { it }
    }
}
