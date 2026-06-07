package com.example.matule.presentation.shop

import com.example.matule.common.catalog.CartManager
import com.example.matule.common.catalog.FavoriteManager
import com.example.matule.domain.model.FilterOptions

/**
 * Purpose: Keeps simple shared in-memory shop state for Sprint 3 UI screens.
 * Creation date: 2026-06-07
 * Author: Mors
 */
object ProductUiState {
    val favoriteManager = FavoriteManager()
    val cartManager = CartManager()

    var filterOptions: FilterOptions = FilterOptions()
    var selectedProductId: String? = null
}
