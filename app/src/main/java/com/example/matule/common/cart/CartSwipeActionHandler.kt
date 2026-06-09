package com.example.matule.common.cart

import com.example.matule.common.catalog.CartManager

/**
 * Purpose: Converts cart item swipe actions into cart changes for Sprint 4.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class CartSwipeActionHandler(
    private val cartManager: CartManager
) {

    /**
     * Purpose: Handles swipe action for product in cart.
     */
    fun handle(productId: String, action: CartSwipeAction) {
        // RED stage placeholder.
    }
}

/**
 * Purpose: Lists supported cart swipe directions.
 * Creation date: 2026-06-09
 * Author: Mors
 */
enum class CartSwipeAction {
    RIGHT,
    LEFT,
    UNKNOWN
}
