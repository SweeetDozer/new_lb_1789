package com.example.matule.common.search

import com.example.matule.domain.model.Product
import com.example.matule.domain.model.ShoeColor
import com.example.matule.domain.model.ShoeType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Purpose: Tests Sprint 4 repeated search cache behavior.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class SearchCacheTest {
    private val product = product("1")

    @Test
    fun firstSearchIsNotFromCache() {
        val cache = SearchCache()

        val result = cache.search("nike") { listOf(product) }

        assertFalse(result.wasFromCache)
        assertEquals(listOf(product), result.products)
    }

    @Test
    fun repeatedSameQueryUsesCache() {
        val cache = SearchCache()

        cache.search("nike") { listOf(product) }
        val result = cache.search("nike") { emptyList() }

        assertTrue(result.wasFromCache)
        assertEquals(listOf(product), result.products)
    }

    @Test
    fun differentQueryDoesNotUseOldCache() {
        val cache = SearchCache()

        cache.search("nike") { listOf(product) }
        val result = cache.search("air") { emptyList() }

        assertFalse(result.wasFromCache)
        assertTrue(result.products.isEmpty())
    }

    private fun product(id: String): Product {
        return Product(
            id = id,
            name = "Nike Air Max",
            category = "Shoes",
            price = 100.0,
            oldPrice = null,
            hasDiscount = false,
            colors = listOf(ShoeColor.BLUE),
            type = ShoeType.SPORT,
            imageName = "$id.png",
            description = "Cache product"
        )
    }
}
