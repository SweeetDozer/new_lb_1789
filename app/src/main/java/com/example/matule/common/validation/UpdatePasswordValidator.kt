package com.example.matule.common.validation

/**
 * Purpose: Validates update-password form after OTP recovery.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class UpdatePasswordValidator(
    private val passwordValidator: PasswordValidator = PasswordValidator(),
    private val captchaValidator: CaptchaValidator
) {

    /**
     * Purpose: Checks password complexity, repeated password, and CAPTCHA.
     */
    fun validate(
        password: String,
        repeatPassword: String,
        captchaInput: String
    ): Sprint2ValidationResult {
        return when {
            password.isBlank() -> Sprint2ValidationResult.Error("Password is required")
            !passwordValidator.isValid(password) -> Sprint2ValidationResult.Error("Password is invalid")
            repeatPassword.isBlank() -> Sprint2ValidationResult.Error("Repeat password is required")
            repeatPassword != password -> Sprint2ValidationResult.Error("Passwords do not match")
            captchaInput.isBlank() -> Sprint2ValidationResult.Error("Captcha is required")
            !captchaValidator.isValid(captchaInput) -> Sprint2ValidationResult.Error("Captcha is invalid")
            else -> Sprint2ValidationResult.Success
        }
    }
}
