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
        if (password.isEmpty()) {
            return PasswordStrength.EMPTY
        }

        val groupsCount = listOf(
            password.any { it in 'A'..'Z' },
            password.any { it in 'a'..'z' },
            password.any { it.isDigit() },
            password.any { !it.isLetterOrDigit() }
        ).count { it }

        return when {
            password.length >= STRONG_MIN_LENGTH && groupsCount == REQUIRED_GROUPS -> PasswordStrength.STRONG
            password.length >= MEDIUM_MIN_LENGTH && groupsCount >= MEDIUM_GROUPS -> PasswordStrength.MEDIUM
            else -> PasswordStrength.WEAK
        }
    }

    private companion object {
        const val MEDIUM_MIN_LENGTH = 8
        const val STRONG_MIN_LENGTH = 8
        const val MEDIUM_GROUPS = 3
        const val REQUIRED_GROUPS = 4
    }
}
