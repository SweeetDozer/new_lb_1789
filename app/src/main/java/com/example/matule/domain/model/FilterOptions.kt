package com.example.matule.domain.model

/**
 * Purpose: Stores selected Sprint 3 filter values before applying them to products.
 * Creation date: 2026-06-07
 * Author: Mors
 */
data class FilterOptions(
    val onlyDiscount: Boolean = false,
    val minPrice: Double? = null,
    val maxPrice: Double? = null,
    val selectedColors: Set<ShoeColor> = emptySet(),
    val selectedTypes: Set<ShoeType> = emptySet()
)
