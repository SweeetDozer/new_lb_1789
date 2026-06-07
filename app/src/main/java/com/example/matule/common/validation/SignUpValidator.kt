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
            isNameEmpty(name) -> Sprint2ValidationResult.Error(NAME_REQUIRED)
            isEmailEmpty(email) -> Sprint2ValidationResult.Error(EMAIL_REQUIRED)
            isEmailInvalid(email) -> Sprint2ValidationResult.Error(EMAIL_INVALID)
            isPasswordEmpty(password) -> Sprint2ValidationResult.Error(PASSWORD_REQUIRED)
            !isAgreementChecked -> Sprint2ValidationResult.Error(AGREEMENT_REQUIRED)
            else -> Sprint2ValidationResult.Success
        }
    }

    private fun isNameEmpty(name: String): Boolean {
        return name.isBlank()
    }

    private fun isEmailEmpty(email: String): Boolean {
        return email.isBlank()
    }

    private fun isEmailInvalid(email: String): Boolean {
        return !emailValidator.isValid(email)
    }

    private fun isPasswordEmpty(password: String): Boolean {
        return password.isBlank()
    }

    private companion object {
        const val NAME_REQUIRED = "Name is required"
        const val EMAIL_REQUIRED = "Email is required"
        const val EMAIL_INVALID = "Email format is invalid"
        const val PASSWORD_REQUIRED = "Password is required"
        const val AGREEMENT_REQUIRED = "Agreement is required"
    }
}
