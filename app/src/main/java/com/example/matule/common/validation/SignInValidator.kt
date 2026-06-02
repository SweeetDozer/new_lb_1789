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
            email.isBlank() -> SignInValidationResult.Error("Email is required")
            password.isBlank() -> SignInValidationResult.Error("Password is required")
            !emailValidator.isValid(email) -> SignInValidationResult.Error("Email format is invalid")
            else -> SignInValidationResult.Success
        }
    }
}
