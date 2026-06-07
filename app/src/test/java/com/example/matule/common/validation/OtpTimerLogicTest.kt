package com.example.matule.common.validation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Purpose: RED tests for simple Sprint 2 OTP timer logic.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class OtpTimerLogicTest {

    private val timerLogic = OtpTimerLogic()

    /**
     * Purpose: Checks initial timer value.
     */
    @Test
    fun initialTimerHasTenSeconds() {
        assertEquals(10, timerLogic.currentSeconds())
    }

    /**
     * Purpose: Checks that resend is blocked before timer reaches zero.
     */
    @Test
    fun resendIsUnavailableBeforeZero() {
        assertFalse(timerLogic.isResendAvailable(5))
    }

    /**
     * Purpose: Checks that resend is allowed at zero.
     */
    @Test
    fun resendIsAvailableAtZero() {
        assertTrue(timerLogic.isResendAvailable(0))
    }
}
