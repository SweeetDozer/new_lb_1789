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
            isPasswordEmpty(password) -> Sprint2ValidationResult.Error(PASSWORD_REQUIRED)
            isPasswordInvalid(password) -> Sprint2ValidationResult.Error(PASSWORD_INVALID)
            isRepeatPasswordEmpty(repeatPassword) -> Sprint2ValidationResult.Error(REPEAT_PASSWORD_REQUIRED)
            arePasswordsDifferent(password, repeatPassword) -> Sprint2ValidationResult.Error(PASSWORDS_DO_NOT_MATCH)
            isCaptchaEmpty(captchaInput) -> Sprint2ValidationResult.Error(CAPTCHA_REQUIRED)
            isCaptchaInvalid(captchaInput) -> Sprint2ValidationResult.Error(CAPTCHA_INVALID)
            else -> Sprint2ValidationResult.Success
        }
    }

    private fun isPasswordEmpty(password: String): Boolean {
        return password.isBlank()
    }

    private fun isPasswordInvalid(password: String): Boolean {
        return !passwordValidator.isValid(password)
    }

    private fun isRepeatPasswordEmpty(repeatPassword: String): Boolean {
        return repeatPassword.isBlank()
    }

    private fun arePasswordsDifferent(password: String, repeatPassword: String): Boolean {
        return repeatPassword != password
    }

    private fun isCaptchaEmpty(captchaInput: String): Boolean {
        return captchaInput.isBlank()
    }

    private fun isCaptchaInvalid(captchaInput: String): Boolean {
        return !captchaValidator.isValid(captchaInput)
    }

    private companion object {
        const val PASSWORD_REQUIRED = "Password is required"
        const val PASSWORD_INVALID = "Password is invalid"
        const val REPEAT_PASSWORD_REQUIRED = "Repeat password is required"
        const val PASSWORDS_DO_NOT_MATCH = "Passwords do not match"
        const val CAPTCHA_REQUIRED = "Captcha is required"
        const val CAPTCHA_INVALID = "Captcha is invalid"
    }
}
