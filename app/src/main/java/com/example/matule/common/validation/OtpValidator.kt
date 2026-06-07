package com.example.matule.common.validation

/**
 * Purpose: Validates OTP code entered during password recovery.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class OtpValidator(
    private val expectedOtp: String,
    private val otpLength: Int = 4
) {

    /**
     * Purpose: Checks code length and equality with expected OTP.
     */
    fun validate(enteredOtp: String): Sprint2ValidationResult {
        TODO("GREEN stage will implement OTP validation")
    }
}
