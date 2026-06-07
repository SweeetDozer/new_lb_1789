package com.example.matule.common.catalog

import com.example.matule.domain.model.CartItem
import com.example.matule.domain.model.Product

/**
 * Purpose: Stores cart products and quantities locally for Sprint 3 cart logic.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class CartManager {

    /**
     * Purpose: Adds product to cart or increases quantity if it already exists.
     */
    fun add(product: Product) = Unit

    /**
     * Purpose: Increases product quantity.
     */
    fun increase(productId: String) = Unit

    /**
     * Purpose: Decreases product quantity without making it negative.
     */
    fun decrease(productId: String) = Unit

    /**
     * Purpose: Removes product from cart.
     */
    fun remove(productId: String) = Unit

    /**
     * Purpose: Returns cart item by product id.
     */
    fun getItem(productId: String): CartItem? {
        return null
    }

    /**
     * Purpose: Calculates total products count in cart.
     */
    fun totalCount(): Int {
        return 0
    }
}
