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
        TODO("GREEN stage will implement update-password validation")
    }
}
