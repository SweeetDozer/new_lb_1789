package com.example.matule.common.validation

/**
 * Purpose: Stores simple OTP resend timer state for Sprint 2.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class OtpTimerLogic(
    private val initialSeconds: Int = 10
) {

    /**
     * Purpose: Returns initial timer value in seconds.
     */
    fun currentSeconds(): Int {
        TODO("GREEN stage will return current OTP timer seconds")
    }

    /**
     * Purpose: Reports whether resend action is available for the provided second.
     */
    fun isResendAvailable(seconds: Int): Boolean {
        TODO("GREEN stage will implement resend availability")
    }
}
