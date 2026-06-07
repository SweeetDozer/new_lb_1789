package com.example.matule.common.catalog

import com.example.matule.domain.model.Product
import com.example.matule.domain.model.ShoeColor
import com.example.matule.domain.model.ShoeType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Purpose: Tests current product switching and highlight index for Sprint 3 product swiper.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class ProductSwiperStateTest {

    private val products = listOf(product("1"), product("2"), product("3"))

    @Test
    fun startsWithFirstProduct() {
        val state = ProductSwiperState(products)

        assertEquals(products.first(), state.currentProduct())
    }

    @Test
    fun nextChangesCurrentProduct() {
        val state = ProductSwiperState(products)

        state.next()

        assertEquals(products[1], state.currentProduct())
    }

    @Test
    fun previousChangesCurrentProduct() {
        val state = ProductSwiperState(products)

        state.next()
        state.previous()

        assertEquals(products[0], state.currentProduct())
    }

    @Test
    fun doesNotGoBeforeFirstProduct() {
        val state = ProductSwiperState(products)

        state.previous()

        assertEquals(0, state.currentIndex)
    }

    @Test
    fun doesNotGoAfterLastProduct() {
        val state = ProductSwiperState(products)

        state.next()
        state.next()
        state.next()

        assertEquals(2, state.currentIndex)
    }

    @Test
    fun currentIndexIsUsedForHighlight() {
        val state = ProductSwiperState(products)

        state.next()

        assertTrue(state.isHighlighted(1))
    }

    private fun product(id: String): Product {
        return Product(
            id = id,
            name = "Product $id",
            category = "Shoes",
            price = 100.0,
            oldPrice = null,
            hasDiscount = false,
            colors = listOf(ShoeColor.BLUE),
            type = ShoeType.CASUAL,
            imageName = "$id.png",
            description = "Product $id description"
        )
    }
}
