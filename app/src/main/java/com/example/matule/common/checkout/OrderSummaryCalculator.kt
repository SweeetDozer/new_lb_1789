package com.example.matule.common.checkout

import com.example.matule.common.catalog.CartManager

/**
 * Purpose: Calculates Sprint 4 checkout prices.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class OrderSummaryCalculator {

    /**
     * Purpose: Calculates product subtotal.
     */
    fun subtotal(cartManager: CartManager): Double {
        return cartManager.totalPrice()
    }

    /**
     * Purpose: Calculates final total with delivery price.
     */
    fun total(cartManager: CartManager, deliveryPrice: Double): Double {
        val subtotal = subtotal(cartManager)
        return if (subtotal == 0.0) {
            0.0
        } else {
            subtotal + deliveryPrice
        }
    }
}
