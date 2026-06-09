package com.example.matule.common.search

import com.example.matule.domain.model.Product

/**
 * Purpose: Searches products for Sprint 4 Search screen.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class SearchEngine {

    /**
     * Purpose: Returns products matching user query.
     */
    fun search(products: List<Product>, query: String): List<Product> {
        val preparedQuery = query.trim().lowercase()
        if (preparedQuery.isEmpty()) {
            return products
        }

        return products.filter { product ->
            product.name.lowercase().contains(preparedQuery) ||
                product.category.lowercase().contains(preparedQuery) ||
                product.type.name.lowercase().contains(preparedQuery) ||
                product.description.lowercase().contains(preparedQuery)
        }
    }
}
