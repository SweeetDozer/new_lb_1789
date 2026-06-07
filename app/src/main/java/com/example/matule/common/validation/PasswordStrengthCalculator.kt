package com.example.matule.common.validation

/**
 * Purpose: Describes simple password strength levels for Sprint 2.
 * Creation date: 2026-06-07
 * Author: Mors
 */
enum class PasswordStrength {
    EMPTY,
    WEAK,
    MEDIUM,
    STRONG
}

/**
 * Purpose: Calculates simple password strength for update-password screen.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class PasswordStrengthCalculator {

    /**
     * Purpose: Returns strength level by password content.
     */
    fun calculate(password: String): PasswordStrength {
        TODO("GREEN stage will implement password strength calculation")
    }
}
