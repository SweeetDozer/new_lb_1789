package com.example.matule.common.checkout

/**
 * Purpose: Represents Sprint 4 order confirmation navigation state.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class OrderConfirmationState {

    /**
     * Purpose: Confirms order and returns confirmation result.
     */
    fun confirm(): OrderConfirmationResult {
        return OrderConfirmationResult(isSuccess = true, returnTarget = HOME_TARGET)
    }

    private companion object {
        const val HOME_TARGET = "Home"
    }
}

/**
 * Purpose: Stores order confirmation result.
 * Creation date: 2026-06-09
 * Author: Mors
 */
data class OrderConfirmationResult(
    val isSuccess: Boolean,
    val returnTarget: String
)
