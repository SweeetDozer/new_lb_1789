package com.example.matule.data.local

import com.example.matule.domain.model.Product
import com.example.matule.domain.model.ShoeColor
import com.example.matule.domain.model.ShoeType

/**
 * Purpose: Provides local mock products for Sprint 3 shop UI.
 * Creation date: 2026-06-07
 * Author: Mors
 */
object ProductMockData {

    /**
     * Purpose: Returns demo shoes used before real backend integration.
     */
    fun products(): List<Product> {
        return listOf(
            product("1", "Nike Air Max", 752.0, true, listOf(ShoeColor.BLUE, ShoeColor.WHITE), ShoeType.SPORT),
            product("2", "Nike Runner", 812.0, false, listOf(ShoeColor.BLACK, ShoeColor.WHITE), ShoeType.SPORT),
            product("3", "Matule Street", 690.0, true, listOf(ShoeColor.RED, ShoeColor.BLACK), ShoeType.CASUAL),
            product("4", "Outdoor Trail", 940.0, false, listOf(ShoeColor.GREEN, ShoeColor.BLACK), ShoeType.SPORT),
            product("5", "Tennis Pro", 880.0, true, listOf(ShoeColor.WHITE, ShoeColor.BLUE), ShoeType.CASUAL),
            product("6", "Daily Comfort", 610.0, false, listOf(ShoeColor.WHITE), ShoeType.CASUAL),
            product("7", "Blue Motion", 720.0, true, listOf(ShoeColor.BLUE), ShoeType.SPORT),
            product("8", "Green Step", 640.0, false, listOf(ShoeColor.GREEN), ShoeType.CASUAL)
        )
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
            oldPrice = if (hasDiscount) price + 120.0 else null,
            hasDiscount = hasDiscount,
            colors = colors,
            type = type,
            imageName = "img_onboard_1",
            description = "$name demo description for Sprint 3 catalog"
        )
    }
}
