package com.example.matule.common.search

import com.example.matule.domain.model.Product
import com.example.matule.domain.model.ShoeColor
import com.example.matule.domain.model.ShoeType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Purpose: Tests Sprint 4 product search rules.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class SearchEngineTest {
    private val products = listOf(
        product("1", "Nike Air Max", "Running", ShoeType.SPORT),
        product("2", "Daily Comfort", "Casual", ShoeType.CASUAL)
    )

    @Test
    fun emptyQueryReturnsAllProducts() {
        val result = SearchEngine().search(products, "")

        assertEquals(products, result)
    }

    @Test
    fun searchIsCaseInsensitive() {
        val result = SearchEngine().search(products, "NIKE")

        assertEquals(listOf(products[0]), result)
    }

    @Test
    fun partialProductNameWorks() {
        val result = SearchEngine().search(products, "air")

        assertEquals(listOf(products[0]), result)
    }

    @Test
    fun unknownQueryReturnsEmptyList() {
        val result = SearchEngine().search(products, "boots")

        assertTrue(result.isEmpty())
    }

    private fun product(id: String, name: String, category: String, type: ShoeType): Product {
        return Product(
            id = id,
            name = name,
            category = category,
            price = 100.0,
            oldPrice = null,
            hasDiscount = false,
            colors = listOf(ShoeColor.WHITE),
            type = type,
            imageName = "$id.png",
            description = "Search product"
        )
    }
}
