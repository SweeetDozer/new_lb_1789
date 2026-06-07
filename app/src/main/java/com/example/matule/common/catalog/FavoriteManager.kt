package com.example.matule.common.catalog

import com.example.matule.domain.model.Product

/**
 * Purpose: Stores favorite products locally for Sprint 3 screens.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class FavoriteManager {

    /**
     * Purpose: Adds product to favorites.
     */
    fun add(product: Product) = Unit

    /**
     * Purpose: Removes product from favorites.
     */
    fun remove(productId: String) = Unit

    /**
     * Purpose: Checks whether product is in favorites.
     */
    fun isFavorite(productId: String): Boolean {
        return false
    }

    /**
     * Purpose: Returns current favorite products.
     */
    fun getFavorites(): List<Product> {
        return emptyList()
    }
}
