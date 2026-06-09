package com.example.matule.common.cart

import com.example.matule.common.catalog.CartManager
import com.example.matule.domain.model.Product
import com.example.matule.domain.model.ShoeColor
import com.example.matule.domain.model.ShoeType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * Purpose: Tests Sprint 4 cart swipe action logic.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class CartSwipeActionHandlerTest {
    private val product = product("1")

    @Test
    fun swipeRightIncreasesQuantity() {
        val manager = CartManager()
        manager.add(product)

        CartSwipeActionHandler(manager).handle(product.id, CartSwipeAction.RIGHT)

        assertEquals(2, manager.getItem(product.id)?.quantity)
    }

    @Test
    fun swipeLeftRemovesItem() {
        val manager = CartManager()
        manager.add(product)

        CartSwipeActionHandler(manager).handle(product.id, CartSwipeAction.LEFT)

        assertNull(manager.getItem(product.id))
    }

    @Test
    fun unknownSwipeDoesNotCrashOrChangeCart() {
        val manager = CartManager()
        manager.add(product)

        CartSwipeActionHandler(manager).handle(product.id, CartSwipeAction.UNKNOWN)

        assertEquals(1, manager.getItem(product.id)?.quantity)
    }

    private fun product(id: String): Product {
        return Product(
            id = id,
            name = "Swipe $id",
            category = "Shoes",
            price = 100.0,
            oldPrice = null,
            hasDiscount = false,
            colors = listOf(ShoeColor.GREEN),
            type = ShoeType.CASUAL,
            imageName = "$id.png",
            description = "Swipe product"
        )
    }
}
