package com.example.matule.common.validation

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Purpose: RED tests for Sprint 2 password strength calculation.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class PasswordStrengthCalculatorTest {

    private val calculator = PasswordStrengthCalculator()

    /**
     * Purpose: Checks empty password strength.
     */
    @Test
    fun emptyPasswordReturnsEmpty() {
        assertEquals(PasswordStrength.EMPTY, calculator.calculate(""))
    }

    /**
     * Purpose: Checks weak password strength.
     */
    @Test
    fun weakPasswordReturnsWeak() {
        assertEquals(PasswordStrength.WEAK, calculator.calculate("abc"))
    }

    /**
     * Purpose: Checks medium password strength.
     */
    @Test
    fun mediumPasswordReturnsMedium() {
        assertEquals(PasswordStrength.MEDIUM, calculator.calculate("Password1"))
    }

    /**
     * Purpose: Checks strong password strength.
     */
    @Test
    fun strongPasswordReturnsStrong() {
        assertEquals(PasswordStrength.STRONG, calculator.calculate("Password1!"))
    }
}
