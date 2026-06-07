package com.example.matule.common.validation

/**
 * Purpose: Validates CAPTCHA text entered on update-password screen.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class CaptchaValidator(
    private val expectedCaptcha: String
) {

    /**
     * Purpose: Checks user input against expected CAPTCHA text.
     */
    fun isValid(userInput: String): Boolean {
        TODO("GREEN stage will implement CAPTCHA validation")
    }
}
