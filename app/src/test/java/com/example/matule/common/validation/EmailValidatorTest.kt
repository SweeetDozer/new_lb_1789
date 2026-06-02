package com.example.matule.common.validation

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Purpose: RED tests for Sprint 1 email format rules.
 * Creation date: 2026-06-03
 * Author: Mors
 */
class EmailValidatorTest {

    private val validator = EmailValidator()

    /**
     * Purpose: Checks accepted lowercase latin email with digits and long enough top-level domain.
     */
    @Test
    fun validEmailReturnsTrue() {
        assertTrue(validator.isValid("test123@mail.ru"))
        assertTrue(validator.isValid("user1@gmail.com"))
    }

    /**
     * Purpose: Checks that uppercase letters are rejected.
     */
    @Test
    fun invalidEmailReturnsFalseForUppercaseLetters() {
        assertFalse(validator.isValid("Test@mail.ru"))
        assertFalse(validator.isValid("test@MAIL.ru"))
    }

    /**
     * Purpose: Checks emails with wrong structure or too-short top-level domain.
     */
    @Test
    fun invalidEmailReturnsFalseForWrongFormat() {
        assertFalse(validator.isValid("test@mail.r"))
        assertFalse(validator.isValid("test@mail"))
        assertFalse(validator.isValid("test@@mail.ru"))
    }
}
