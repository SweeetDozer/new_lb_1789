package com.example.matule.common.validation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Purpose: RED tests for simple sign-in form validation.
 * Creation date: 2026-06-03
 * Author: Mors
 */
class SignInValidatorTest {

    private val validator = SignInValidator()

    /**
     * Purpose: Checks that empty email is reported as an error.
     */
    @Test
    fun emptyEmailReturnsError() {
        val result = validator.validate("", "password123")

        assertTrue(result is SignInValidationResult.Error)
        assertEquals("Email is required", (result as SignInValidationResult.Error).message)
    }

    /**
     * Purpose: Checks that empty password is reported as an error.
     */
    @Test
    fun emptyPasswordReturnsError() {
        val result = validator.validate("test123@mail.ru", "")

        assertTrue(result is SignInValidationResult.Error)
        assertEquals("Password is required", (result as SignInValidationResult.Error).message)
    }

    /**
     * Purpose: Checks that invalid email format is reported as an error.
     */
    @Test
    fun invalidEmailReturnsError() {
        val result = validator.validate("Test@mail.ru", "password123")

        assertTrue(result is SignInValidationResult.Error)
        assertEquals("Email format is invalid", (result as SignInValidationResult.Error).message)
    }

    /**
     * Purpose: Checks that correct email and non-empty password pass validation.
     */
    @Test
    fun validEmailAndNonEmptyPasswordReturnsSuccess() {
        val result = validator.validate("test123@mail.ru", "password123")

        assertEquals(SignInValidationResult.Success, result)
    }
}
