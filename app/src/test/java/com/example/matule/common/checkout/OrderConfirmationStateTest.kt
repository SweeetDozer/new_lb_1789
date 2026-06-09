package com.example.matule.common.checkout

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Purpose: Tests Sprint 4 order confirmation state.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class OrderConfirmationStateTest {

    @Test
    fun confirmOrderReturnsSuccessState() {
        val result = OrderConfirmationState().confirm()

        assertTrue(result.isSuccess)
    }

    @Test
    fun returnToShoppingTargetIsHome() {
        val result = OrderConfirmationState().confirm()

        assertEquals("Home", result.returnTarget)
    }
}
