package com.example.matule.common.validation

/**
 * Purpose: Stores simple OTP resend timer state for Sprint 2.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class OtpTimerLogic(
    private val initialSeconds: Int = 10
) {
    private var seconds: Int = initialSeconds

    /**
     * Purpose: Returns initial timer value in seconds.
     */
    fun currentSeconds(): Int {
        return seconds
    }

    /**
     * Purpose: Reports whether resend action is available for the provided second.
     */
    fun isResendAvailable(seconds: Int): Boolean {
        return seconds == 0
    }

    /**
     * Purpose: Decreases timer by one second without going below zero.
     */
    fun tick(): Int {
        if (seconds > 0) {
            seconds--
        }
        return seconds
    }
}
