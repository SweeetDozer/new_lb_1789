package com.example.matule.common.validation

/**
 * Purpose: Validates email format for Sprint 1 sign-in rules.
 * Creation date: 2026-06-03
 * Author: Mors
 */
class EmailValidator {
    private val emailPattern = Regex("^[a-z0-9]+@[a-z0-9]+\\.[a-z]{2,}$")

    /**
     * Purpose: Checks the pattern name@domain.tld with lowercase latin letters and digits only.
     */
    fun isValid(email: String): Boolean = emailPattern.matches(email)
}
