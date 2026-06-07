package com.example.matule.domain.model

/**
 * Purpose: Stores product data used by Sprint 3 catalog, favorites, cart, and details logic.
 * Creation date: 2026-06-07
 * Author: Mors
 */
data class Product(
    val id: String,
    val name: String,
    val category: String,
    val price: Double,
    val oldPrice: Double?,
    val hasDiscount: Boolean,
    val colors: List<ShoeColor>,
    val type: ShoeType,
    val imageName: String,
    val description: String,
    val isFavorite: Boolean = false,
    val cartQuantity: Int = 0
)
