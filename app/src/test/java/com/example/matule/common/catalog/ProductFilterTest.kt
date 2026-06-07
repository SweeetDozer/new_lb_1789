package com.example.matule.common.catalog

import com.example.matule.domain.model.FilterOptions
import com.example.matule.domain.model.Product
import com.example.matule.domain.model.ShoeColor
import com.example.matule.domain.model.ShoeType
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Purpose: Tests product filtering rules for Sprint 3 Catalog and Popular screens.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class ProductFilterTest {

    private val filter = ProductFilter()
    private val products = listOf(
        product("1", "Classic", 120.0, true, listOf(ShoeColor.BLACK), ShoeType.CASUAL),
        product("2", "Runner", 220.0, false, listOf(ShoeColor.WHITE, ShoeColor.BLUE), ShoeType.SPORT),
        product("3", "Street", 180.0, true, listOf(ShoeColor.RED), ShoeType.CASUAL),
        product("4", "Trail", 300.0, false, listOf(ShoeColor.GREEN, ShoeColor.BLACK), ShoeType.SPORT)
    )

    /**
     * Purpose: Discount filter should keep only discounted products.
     */
    @Test
    fun filterByDiscountReturnsOnlyDiscountedProducts() {
        val result = filter.filter(products, FilterOptions(onlyDiscount = true))

        assertEquals(listOf("1", "3"), result.map { it.id })
    }

    @Test
    fun filterByMinPriceReturnsProductsGreaterOrEqualMin() {
        val result = filter.filter(products, FilterOptions(minPrice = 200.0))

        assertEquals(listOf("2", "4"), result.map { it.id })
    }

    @Test
    fun filterByMaxPriceReturnsProductsLessOrEqualMax() {
        val result = filter.filter(products, FilterOptions(maxPrice = 180.0))

        assertEquals(listOf("1", "3"), result.map { it.id })
    }

    @Test
    fun filterByPriceRangeReturnsProductsInsideRange() {
        val result = filter.filter(products, FilterOptions(minPrice = 150.0, maxPrice = 230.0))

        assertEquals(listOf("2", "3"), result.map { it.id })
    }

    @Test
    fun filterByColorReturnsProductsWithSelectedColor() {
        val result = filter.filter(products, FilterOptions(selectedColors = setOf(ShoeColor.BLACK)))

        assertEquals(listOf("1", "4"), result.map { it.id })
    }

    @Test
    fun filterByTypeReturnsProductsOfSelectedType() {
        val result = filter.filter(products, FilterOptions(selectedTypes = setOf(ShoeType.SPORT)))

        assertEquals(listOf("2", "4"), result.map { it.id })
    }

    @Test
    fun combinedFiltersReturnCorrectProducts() {
        val options = FilterOptions(
            onlyDiscount = true,
            minPrice = 100.0,
            maxPrice = 190.0,
            selectedColors = setOf(ShoeColor.RED),
            selectedTypes = setOf(ShoeType.CASUAL)
        )

        val result = filter.filter(products, options)

        assertEquals(listOf("3"), result.map { it.id })
    }

    @Test
    fun emptyFiltersReturnAllProducts() {
        val result = filter.filter(products, FilterOptions())

        assertEquals(products, result)
    }

    private fun product(
        id: String,
        name: String,
        price: Double,
        hasDiscount: Boolean,
        colors: List<ShoeColor>,
        type: ShoeType
    ): Product {
        return Product(
            id = id,
            name = name,
            category = "Shoes",
            price = price,
            oldPrice = if (hasDiscount) price + 30.0 else null,
            hasDiscount = hasDiscount,
            colors = colors,
            type = type,
            imageName = "$id.png",
            description = "$name description"
        )
    }
}
