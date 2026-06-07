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
        return products.filter { product -> matchesProduct(product, options) }
    }

    private fun matchesProduct(product: Product, options: FilterOptions): Boolean {
        return matchesDiscount(product, options) &&
            matchesMinPrice(product, options) &&
            matchesMaxPrice(product, options) &&
            matchesColors(product, options) &&
            matchesTypes(product, options)
    }

    private fun matchesDiscount(product: Product, options: FilterOptions): Boolean {
        return !options.onlyDiscount || product.hasDiscount
    }

    private fun matchesMinPrice(product: Product, options: FilterOptions): Boolean {
        return options.minPrice == null || product.price >= options.minPrice
    }

    private fun matchesMaxPrice(product: Product, options: FilterOptions): Boolean {
        return options.maxPrice == null || product.price <= options.maxPrice
    }

    private fun matchesColors(product: Product, options: FilterOptions): Boolean {
        return options.selectedColors.isEmpty() || product.colors.any { it in options.selectedColors }
    }

    private fun matchesTypes(product: Product, options: FilterOptions): Boolean {
        return options.selectedTypes.isEmpty() || product.type in options.selectedTypes
    }
}
