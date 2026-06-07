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
            isEmailEmpty(email) -> Sprint2ValidationResult.Error(EMAIL_REQUIRED)
            isEmailInvalid(email) -> Sprint2ValidationResult.Error(EMAIL_INVALID)
            else -> Sprint2ValidationResult.Success
        }
    }

    private fun isEmailEmpty(email: String): Boolean {
        return email.isBlank()
    }

    private fun isEmailInvalid(email: String): Boolean {
        return !emailValidator.isValid(email)
    }

    private companion object {
        const val EMAIL_REQUIRED = "Email is required"
        const val EMAIL_INVALID = "Email format is invalid"
    }
}
