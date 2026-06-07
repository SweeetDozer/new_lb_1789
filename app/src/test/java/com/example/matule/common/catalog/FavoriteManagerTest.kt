package com.example.matule.common.catalog

import com.example.matule.domain.model.Product
import com.example.matule.domain.model.ShoeColor
import com.example.matule.domain.model.ShoeType
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Purpose: Tests local favorite product behavior for Sprint 3 Favorite screen.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class FavoriteManagerTest {

    private val product = product("1")

    @Test
    fun addProductToFavorites() {
        val manager = FavoriteManager()

        manager.add(product)

        assertEquals(listOf(product), manager.getFavorites())
    }

    @Test
    fun addingSameProductTwiceDoesNotDuplicateIt() {
        val manager = FavoriteManager()

        manager.add(product)
        manager.add(product)

        assertEquals(1, manager.getFavorites().size)
    }

    @Test
    fun removeProductFromFavorites() {
        val manager = FavoriteManager()

        manager.add(product)
        manager.remove(product.id)

        assertTrue(manager.getFavorites().isEmpty())
    }

    @Test
    fun isFavoriteReturnsCorrectResult() {
        val manager = FavoriteManager()

        manager.add(product)

        assertTrue(manager.isFavorite(product.id))
        assertFalse(manager.isFavorite("unknown"))
    }

    private fun product(id: String): Product {
        return Product(
            id = id,
            name = "Favorite",
            category = "Shoes",
            price = 100.0,
            oldPrice = null,
            hasDiscount = false,
            colors = listOf(ShoeColor.BLACK),
            type = ShoeType.CASUAL,
            imageName = "favorite.png",
            description = "Favorite product"
        )
    }
}
