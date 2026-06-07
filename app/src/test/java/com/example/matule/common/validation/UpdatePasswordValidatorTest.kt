package com.example.matule.common.validation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Purpose: RED tests for Sprint 2 update-password validation.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class UpdatePasswordValidatorTest {

    private val validator = UpdatePasswordValidator(
        captchaValidator = CaptchaValidator(expectedCaptcha = "A7B9")
    )

    /**
     * Purpose: Checks that different passwords return error.
     */
    @Test
    fun differentPasswordsReturnsError() {
        val result = validator.validate("Password1!", "Password2!", "A7B9")

        assertTrue(result is Sprint2ValidationResult.Error)
        assertEquals("Passwords do not match", (result as Sprint2ValidationResult.Error).message)
    }

    /**
     * Purpose: Checks that invalid CAPTCHA returns error.
     */
    @Test
    fun invalidCaptchaReturnsError() {
        val result = validator.validate("Password1!", "Password1!", "wrong")

        assertTrue(result is Sprint2ValidationResult.Error)
        assertEquals("Captcha is invalid", (result as Sprint2ValidationResult.Error).message)
    }

    /**
     * Purpose: Checks that valid password, repeated password, and CAPTCHA pass validation.
     */
    @Test
    fun validPasswordMatchingRepeatAndCorrectCaptchaReturnsSuccess() {
        val result = validator.validate("Password1!", "Password1!", "A7B9")

        assertEquals(Sprint2ValidationResult.Success, result)
    }
}
