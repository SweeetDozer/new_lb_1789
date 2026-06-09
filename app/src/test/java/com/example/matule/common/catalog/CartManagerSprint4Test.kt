package com.example.matule.common.catalog

import com.example.matule.domain.model.Product
import com.example.matule.domain.model.ShoeColor
import com.example.matule.domain.model.ShoeType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * Purpose: Tests extended Sprint 4 cart totals and empty state.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class CartManagerSprint4Test {

    @Test
    fun totalPriceIsCorrect() {
        val manager = CartManager()
        val first = product("1", 100.0)
        val second = product("2", 50.0)

        manager.add(first)
        manager.add(first)
        manager.add(second)

        assertEquals(250.0, manager.totalPrice(), 0.0)
    }

    @Test
    fun emptyCartTotalIsZero() {
        val manager = CartManager()

        assertEquals(0, manager.totalCount())
        assertEquals(0.0, manager.totalPrice(), 0.0)
    }

    @Test
    fun removeItemWorksForSprint4Delete() {
        val manager = CartManager()
        val product = product("1", 100.0)

        manager.add(product)
        manager.remove(product.id)

        assertNull(manager.getItem(product.id))
    }

    private fun product(id: String, price: Double): Product {
        return Product(
            id = id,
            name = "Cart $id",
            category = "Shoes",
            price = price,
            oldPrice = null,
            hasDiscount = false,
            colors = listOf(ShoeColor.BLACK),
            type = ShoeType.SPORT,
            imageName = "$id.png",
            description = "Cart product"
        )
    }
}
