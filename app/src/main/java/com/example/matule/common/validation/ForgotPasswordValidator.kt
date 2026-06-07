package com.example.matule.common.validation

/**
 * Purpose: Validates email field on Sprint 2 forgot-password screen.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class ForgotPasswordValidator(
    private val emailValidator: EmailValidator = EmailValidator()
) {

    /**
     * Purpose: Checks that email is filled and has valid format.
     */
    fun validate(email: String): Sprint2ValidationResult {
        TODO("GREEN stage will implement forgot-password validation")
    }
}
