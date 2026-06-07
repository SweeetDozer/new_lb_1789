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
        return when {
            email.isBlank() -> Sprint2ValidationResult.Error("Email is required")
            !emailValidator.isValid(email) -> Sprint2ValidationResult.Error("Email format is invalid")
            else -> Sprint2ValidationResult.Success
        }
    }
}
