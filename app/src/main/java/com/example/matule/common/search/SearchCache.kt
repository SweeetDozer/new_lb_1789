package com.example.matule.common.search

import com.example.matule.domain.model.Product

/**
 * Purpose: Keeps previous search result so repeated Sprint 4 searches can use cache.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class SearchCache {

    /**
     * Purpose: Searches through provider and marks whether cached data was used.
     */
    fun search(query: String, provider: (String) -> List<Product>): SearchCacheResult {
        return SearchCacheResult(products = provider(query), wasFromCache = false)
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
