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
            hasInvalidLength(enteredOtp) -> Sprint2ValidationResult.Error(OTP_LENGTH_INVALID)
            isIncorrectOtp(enteredOtp) -> Sprint2ValidationResult.Error(OTP_INCORRECT)
            else -> Sprint2ValidationResult.Success
        }
    }

    private fun hasInvalidLength(enteredOtp: String): Boolean {
        return enteredOtp.length != otpLength
    }

    private fun isIncorrectOtp(enteredOtp: String): Boolean {
        return enteredOtp != expectedOtp
    }

    private companion object {
        const val OTP_LENGTH_INVALID = "OTP length is invalid"
        const val OTP_INCORRECT = "OTP is incorrect"
    }
}
