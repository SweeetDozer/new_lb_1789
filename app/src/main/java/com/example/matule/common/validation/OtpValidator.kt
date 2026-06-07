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
        return when {
            enteredOtp.length != otpLength -> Sprint2ValidationResult.Error("OTP length is invalid")
            enteredOtp != expectedOtp -> Sprint2ValidationResult.Error("OTP is incorrect")
            else -> Sprint2ValidationResult.Success
        }
    }
}
