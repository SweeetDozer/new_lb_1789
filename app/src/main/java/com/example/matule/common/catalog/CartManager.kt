package com.example.matule.common.catalog

import com.example.matule.domain.model.CartItem
import com.example.matule.domain.model.Product

/**
 * Purpose: Stores cart products and quantities locally for Sprint 3 cart logic.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class CartManager {
    private val cartItems = linkedMapOf<String, CartItem>()

    /**
     * Purpose: Adds product to cart or increases quantity if it already exists.
     */
    fun add(product: Product) {
        updateQuantity(product.id) { currentItem ->
            if (currentItem == null) {
                CartItem(product = product, quantity = MIN_QUANTITY)
            } else {
                currentItem.copy(quantity = currentItem.quantity + 1)
            }
        }
    }

    /**
     * Purpose: Increases product quantity.
     */
    fun increase(productId: String) {
        updateQuantity(productId) { currentItem ->
            currentItem?.copy(quantity = currentItem.quantity + 1)
        }
    }

    /**
     * Purpose: Decreases product quantity without making it negative.
     */
    fun decrease(productId: String) {
        updateQuantity(productId) { currentItem ->
            when {
                currentItem == null -> null
                currentItem.quantity > MIN_QUANTITY -> currentItem.copy(quantity = currentItem.quantity - 1)
                else -> currentItem
            }
        }
    }

    /**
     * Purpose: Removes product from cart.
     */
    fun remove(productId: String) {
        cartItems.remove(productId)
    }

    /**
     * Purpose: Returns cart item by product id.
     */
    fun getItem(productId: String): CartItem? {
        return cartItems[productId]
    }

    /**
     * Purpose: Calculates total products count in cart.
     */
    fun totalCount(): Int {
        return cartItems.values.sumOf { it.quantity }
    }

    private fun updateQuantity(productId: String, update: (CartItem?) -> CartItem?) {
        val updatedItem = update(cartItems[productId])
        if (updatedItem != null) {
            cartItems[productId] = updatedItem
        }
    }

    private companion object {
        const val MIN_QUANTITY = 1
    }
}
