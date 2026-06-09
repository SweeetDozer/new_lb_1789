package com.example.matule.common.search

import com.example.matule.domain.model.Product

/**
 * Purpose: Keeps previous search result so repeated Sprint 4 searches can use cache.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class SearchCache {
    private var lastQuery: String? = null
    private var lastProducts: List<Product> = emptyList()

    /**
     * Purpose: Searches through provider and marks whether cached data was used.
     */
    fun search(query: String, provider: (String) -> List<Product>): SearchCacheResult {
        val cacheKey = query.trim().lowercase()
        if (lastQuery == cacheKey) {
            return SearchCacheResult(products = lastProducts, wasFromCache = true)
        }

        val products = provider(query)
        lastQuery = cacheKey
        lastProducts = products
        return SearchCacheResult(products = products, wasFromCache = false)
    }
}

/**
 * Purpose: Describes cached search result and cache usage flag.
 * Creation date: 2026-06-09
 * Author: Mors
 */
data class SearchCacheResult(
    val products: List<Product>,
    val wasFromCache: Boolean
)
