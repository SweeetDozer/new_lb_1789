package com.example.matule.common.validation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Purpose: RED tests for Sprint 2 sign-up validation.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class SignUpValidatorTest {

    private val validator = SignUpValidator()

    /**
     * Purpose: Checks that empty name blocks registration.
     */
    @Test
    fun emptyNameReturnsError() {
        val result = validator.validate("", "test123@mail.ru", "Password1!", true)

        assertTrue(result is Sprint2ValidationResult.Error)
        assertEquals("Name is required", (result as Sprint2ValidationResult.Error).message)
    }

    /**
     * Purpose: Checks that invalid email blocks registration.
     */
    @Test
    fun invalidEmailReturnsError() {
        val result = validator.validate("User", "Test@mail.ru", "Password1!", true)

        assertTrue(result is Sprint2ValidationResult.Error)
        assertEquals("Email format is invalid", (result as Sprint2ValidationResult.Error).message)
    }

    /**
     * Purpose: Checks that privacy agreement is required.
     */
    @Test
    fun uncheckedAgreementReturnsError() {
        val result = validator.validate("User", "test123@mail.ru", "Password1!", false)

        assertTrue(result is Sprint2ValidationResult.Error)
        assertEquals("Agreement is required", (result as Sprint2ValidationResult.Error).message)
    }

    /**
     * Purpose: Checks that valid sign-up data passes validation.
     */
    @Test
    fun validDataReturnsSuccess() {
        val result = validator.validate("User", "test123@mail.ru", "Password1!", true)

        assertEquals(Sprint2ValidationResult.Success, result)
    }
}
