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
        val cacheKey = normalizeKey(query)
        if (isCacheHit(cacheKey)) {
            return cachedResult()
        }

        val products = provider(query)
        save(cacheKey, products)
        return SearchCacheResult(products = products, wasFromCache = false)
    }

    private fun normalizeKey(query: String): String {
        return query.trim().lowercase()
    }

    private fun isCacheHit(cacheKey: String): Boolean {
        return lastQuery == cacheKey
    }

    private fun cachedResult(): SearchCacheResult {
        return SearchCacheResult(products = lastProducts, wasFromCache = true)
    }

    private fun save(cacheKey: String, products: List<Product>) {
        lastQuery = cacheKey
        lastProducts = products
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
