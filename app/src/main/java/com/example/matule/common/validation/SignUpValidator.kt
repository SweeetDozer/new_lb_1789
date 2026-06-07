package com.example.matule.common.validation

/**
 * Purpose: Validates Sprint 2 sign-up fields before registration.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class SignUpValidator(
    private val emailValidator: EmailValidator = EmailValidator()
) {

    /**
     * Purpose: Checks name, email, password, and privacy agreement.
     */
    fun validate(
        name: String,
        email: String,
        password: String,
        isAgreementChecked: Boolean
    ): Sprint2ValidationResult {
        return when {
            name.isBlank() -> Sprint2ValidationResult.Error("Name is required")
            email.isBlank() -> Sprint2ValidationResult.Error("Email is required")
            !emailValidator.isValid(email) -> Sprint2ValidationResult.Error("Email format is invalid")
            password.isBlank() -> Sprint2ValidationResult.Error("Password is required")
            !isAgreementChecked -> Sprint2ValidationResult.Error("Agreement is required")
            else -> Sprint2ValidationResult.Success
        }
    }
}
