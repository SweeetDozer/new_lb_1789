package com.example.matule.common.catalog

import com.example.matule.domain.model.Product

/**
 * Purpose: Stores favorite products locally for Sprint 3 screens.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class FavoriteManager {
    private val favorites = linkedMapOf<String, Product>()

    /**
     * Purpose: Adds product to favorites.
     */
    fun add(product: Product) {
        favorites[productKey(product)] = product
    }

    /**
     * Purpose: Removes product from favorites.
     */
    fun remove(productId: String) {
        favorites.remove(productId)
    }

    /**
     * Purpose: Checks whether product is in favorites.
     */
    fun isFavorite(productId: String): Boolean {
        return favorites.containsKey(productId)
    }

    /**
     * Purpose: Returns current favorite products.
     */
    fun getFavorites(): List<Product> {
        return favorites.values.toList()
    }

    private fun productKey(product: Product): String {
        return product.id
    }
}
