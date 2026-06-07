package com.example.matule.common.catalog

import com.example.matule.domain.model.Product

/**
 * Purpose: Stores current product index for Sprint 3 product switching.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class ProductSwiperState(
    private val products: List<Product>
) {
    var currentIndex: Int = 0
        private set

    /**
     * Purpose: Returns currently selected product or null for empty list.
     */
    fun currentProduct(): Product? {
        return products.getOrNull(currentIndex)
    }

    /**
     * Purpose: Moves selection to next product.
     */
    fun next() {
        if (products.isEmpty()) {
            return
        }
        currentIndex = (currentIndex + 1).coerceAtMost(products.lastIndex)
    }

    /**
     * Purpose: Moves selection to previous product.
     */
    fun previous() {
        currentIndex = (currentIndex - 1).coerceAtLeast(FIRST_INDEX)
    }

    /**
     * Purpose: Reports whether index should be highlighted in UI.
     */
    fun isHighlighted(index: Int): Boolean {
        return index == currentIndex
    }

    private companion object {
        const val FIRST_INDEX = 0
    }
}
