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
        return if (isEmptySubtotal(subtotal)) {
            0.0
        } else {
            addDelivery(subtotal, deliveryPrice)
        }
    }

    private fun isEmptySubtotal(subtotal: Double): Boolean {
        return subtotal == 0.0
    }

    private fun addDelivery(subtotal: Double, deliveryPrice: Double): Double {
        return subtotal + deliveryPrice
    }
}
