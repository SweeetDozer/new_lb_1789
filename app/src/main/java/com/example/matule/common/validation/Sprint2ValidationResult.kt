package com.example.matule.common.validation

/**
 * Purpose: Represents simple validation result for Sprint 2 form logic.
 * Creation date: 2026-06-07
 * Author: Mors
 */
sealed class Sprint2ValidationResult {
    /**
     * Purpose: Shows that input passed local validation.
     */
    object Success : Sprint2ValidationResult()

    /**
     * Purpose: Holds readable validation error text for tests and UI.
     */
    data class Error(val message: String) : Sprint2ValidationResult()
}
