package com.example.matule.common.validation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Purpose: RED tests for Sprint 2 forgot-password validation.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class ForgotPasswordValidatorTest {

    private val validator = ForgotPasswordValidator()

    /**
     * Purpose: Checks that empty email is rejected.
     */
    @Test
    fun emptyEmailReturnsError() {
        val result = validator.validate("")

        assertTrue(result is Sprint2ValidationResult.Error)
        assertEquals("Email is required", (result as Sprint2ValidationResult.Error).message)
    }

    /**
     * Purpose: Checks that invalid email is rejected.
     */
    @Test
    fun invalidEmailReturnsError() {
        val result = validator.validate("Test@mail.ru")

        assertTrue(result is Sprint2ValidationResult.Error)
        assertEquals("Email format is invalid", (result as Sprint2ValidationResult.Error).message)
    }

    /**
     * Purpose: Checks that valid email passes validation.
     */
    @Test
    fun validEmailReturnsSuccess() {
        val result = validator.validate("test123@mail.ru")

        assertEquals(Sprint2ValidationResult.Success, result)
    }
}
