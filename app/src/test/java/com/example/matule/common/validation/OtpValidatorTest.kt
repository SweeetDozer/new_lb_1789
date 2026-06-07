package com.example.matule.common.validation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Purpose: RED tests for Sprint 2 OTP validation.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class OtpValidatorTest {

    private val validator = OtpValidator(expectedOtp = "1234", otpLength = 4)

    /**
     * Purpose: Checks that wrong OTP returns error.
     */
    @Test
    fun wrongCodeReturnsError() {
        val result = validator.validate("1111")

        assertTrue(result is Sprint2ValidationResult.Error)
        assertEquals("OTP is incorrect", (result as Sprint2ValidationResult.Error).message)
    }

    /**
     * Purpose: Checks that correct OTP passes validation.
     */
    @Test
    fun correctCodeReturnsSuccess() {
        val result = validator.validate("1234")

        assertEquals(Sprint2ValidationResult.Success, result)
    }

    /**
     * Purpose: Checks that incomplete OTP returns error.
     */
    @Test
    fun incompleteCodeReturnsError() {
        val result = validator.validate("12")

        assertTrue(result is Sprint2ValidationResult.Error)
        assertEquals("OTP length is invalid", (result as Sprint2ValidationResult.Error).message)
    }
}
