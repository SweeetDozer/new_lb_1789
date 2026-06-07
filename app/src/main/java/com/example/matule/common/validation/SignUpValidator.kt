package com.example.matule.common.validation

/**
 * Purpose: Validates Sprint 2 sign-up fields before registration.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class SignUpValidator(
    private val emailValidator: EmailValidator = EmailValidator()
) {

    /**
     * Purpose: Checks name, email, password, and privacy agreement.
     */
    fun validate(
        name: String,
        email: String,
        password: String,
        isAgreementChecked: Boolean
    ): Sprint2ValidationResult {
        TODO("GREEN stage will implement sign-up validation")
    }
}
