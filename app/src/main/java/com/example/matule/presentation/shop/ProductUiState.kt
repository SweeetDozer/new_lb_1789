package com.example.matule.presentation.shop

import com.example.matule.common.catalog.CartManager
import com.example.matule.common.catalog.FavoriteManager

/**
 * Purpose: Keeps simple shared in-memory shop state for Sprint 3 UI part 1.
 * Creation date: 2026-06-07
 * Author: Mors
 */
object ProductUiState {
    val favoriteManager = FavoriteManager()
    val cartManager = CartManager()
}
