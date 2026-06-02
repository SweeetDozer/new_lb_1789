package com.example.matule.common.validation

/**
 * Purpose: Represents simple sign-in validation result for the presentation layer.
 * Creation date: 2026-06-03
 * Author: Mors
 */
sealed class SignInValidationResult {
    /**
     * Purpose: Shows that entered credentials passed local validation.
     */
    object Success : SignInValidationResult()

    /**
     * Purpose: Holds a readable error for invalid sign-in form input.
     */
    data class Error(val message: String) : SignInValidationResult()
}

/**
 * Purpose: Checks email and password fields before a sign-in request.
 * Creation date: 2026-06-03
 * Author: Mors
 */
class SignInValidator(
    private val emailValidator: EmailValidator = EmailValidator()
) {

    /**
     * Purpose: Validates empty fields and email format.
     */
    fun validate(email: String, password: String): SignInValidationResult {
        return when {
            isEmpty(email) -> SignInValidationResult.Error(EMAIL_REQUIRED_ERROR)
            isEmpty(password) -> SignInValidationResult.Error(PASSWORD_REQUIRED_ERROR)
            isInvalidEmail(email) -> SignInValidationResult.Error(INVALID_EMAIL_ERROR)
            else -> SignInValidationResult.Success
        }
    }

    /**
     * Purpose: Checks whether a form field has no visible text.
     */
    private fun isEmpty(value: String): Boolean = value.isBlank()

    /**
     * Purpose: Keeps email format checking delegated to EmailValidator.
     */
    private fun isInvalidEmail(email: String): Boolean = !emailValidator.isValid(email)

    private companion object {
        const val EMAIL_REQUIRED_ERROR = "Email is required"
        const val PASSWORD_REQUIRED_ERROR = "Password is required"
        const val INVALID_EMAIL_ERROR = "Email format is invalid"
    }
}
