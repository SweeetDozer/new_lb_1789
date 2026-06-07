package com.example.matule.common.validation

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Purpose: RED tests for Sprint 2 password complexity validation.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class PasswordValidatorTest {

    private val validator = PasswordValidator()

    /**
     * Purpose: Checks minimum password length.
     */
    @Test
    fun passwordShorterThanEightIsInvalid() {
        assertFalse(validator.isValid("Aa1!"))
    }

    /**
     * Purpose: Checks uppercase letter requirement.
     */
    @Test
    fun passwordWithoutUppercaseIsInvalid() {
        assertFalse(validator.isValid("password1!"))
    }

    /**
     * Purpose: Checks lowercase letter requirement.
     */
    @Test
    fun passwordWithoutLowercaseIsInvalid() {
        assertFalse(validator.isValid("PASSWORD1!"))
    }

    /**
     * Purpose: Checks digit requirement.
     */
    @Test
    fun passwordWithoutDigitIsInvalid() {
        assertFalse(validator.isValid("Password!"))
    }

    /**
     * Purpose: Checks special character requirement.
     */
    @Test
    fun passwordWithoutSpecialCharacterIsInvalid() {
        assertFalse(validator.isValid("Password1"))
    }

    /**
     * Purpose: Checks valid strong password.
     */
    @Test
    fun validStrongPasswordIsValid() {
        assertTrue(validator.isValid("Password1!"))
    }
}
