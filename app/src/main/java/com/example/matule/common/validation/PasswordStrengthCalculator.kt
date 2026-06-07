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

        val groupsCount = PasswordRules.matchedGroupsCount(password)

        return when {
            PasswordRules.hasMinLength(password) && groupsCount == REQUIRED_GROUPS -> PasswordStrength.STRONG
            PasswordRules.hasMinLength(password) && groupsCount >= MEDIUM_GROUPS -> PasswordStrength.MEDIUM
            else -> PasswordStrength.WEAK
        }
    }

    private companion object {
        const val MEDIUM_GROUPS = 3
        const val REQUIRED_GROUPS = 4
    }
}
