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
        val preparedQuery = normalize(query)
        if (isEmptyQuery(preparedQuery)) {
            return products
        }

        return products.filter { product -> product.matches(preparedQuery) }
    }

    private fun Product.matches(query: String): Boolean {
        return listOf(name, category, type.name, description)
            .any { field -> normalize(field).contains(query) }
    }

    private fun normalize(value: String): String {
        return value.trim().lowercase()
    }

    private fun isEmptyQuery(query: String): Boolean {
        return query.isEmpty()
    }
}
