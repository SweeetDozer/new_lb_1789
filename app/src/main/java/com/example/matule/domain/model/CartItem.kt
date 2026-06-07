package com.example.matule.domain.model

/**
 * Purpose: Stores product and quantity inside the Sprint 3 cart.
 * Creation date: 2026-06-07
 * Author: Mors
 */
data class CartItem(
    val product: Product,
    val quantity: Int
)
