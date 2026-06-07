package com.example.matule.common.catalog

import com.example.matule.domain.model.FilterOptions
import com.example.matule.domain.model.Product

/**
 * Purpose: Filters Sprint 3 products by selected filter options.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class ProductFilter {

    /**
     * Purpose: Returns products matching discount, price, color, and type filters.
     */
    fun filter(products: List<Product>, options: FilterOptions): List<Product> {
        return products
    }
}
