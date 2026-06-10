package com.example.matule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import com.example.matule.data.local.ProductMockData
import com.example.matule.presentation.orders.OrderDetailFragment
import com.example.matule.presentation.shop.ProductUiState

/**
 * Purpose: Main Activity that hosts Sprint 1 XML screens through Navigation Component.
 * Creation date: 2026-06-03
 * Author: Mors
 */
class MainActivity : AppCompatActivity() {

    /**
     * Purpose: Opens the layout with NavHostFragment.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openSprint5RouteIfNeeded()
    }

    /**
     * Purpose: Opens Sprint 5 screens directly for local ADB screenshot verification.
     */
    private fun openSprint5RouteIfNeeded() {
        val data = intent?.data ?: return
        val navHost = supportFragmentManager.findFragmentById(R.id.navHostFragment) as? NavHostFragment ?: return
        val navController = navHost.navController
        window.decorView.post {
            when (data.host) {
                "home" -> navController.navigate(R.id.homeFragment)
                "catalog" -> navController.navigate(R.id.catalogFragment)
                "details" -> {
                    val product = prepareDemoProduct()
                    navController.navigate(
                        R.id.detailsFragment,
                        bundleOf("product_id" to product.id)
                    )
                }
                "filters" -> navController.navigate(R.id.filtersFragment)
                "search" -> navController.navigate(R.id.searchFragment)
                "favorite" -> navController.navigate(R.id.favoriteFragment)
                "favorite-with-product" -> {
                    prepareDemoProduct(addToFavorites = true)
                    navController.navigate(R.id.favoriteFragment)
                }
                "cart" -> navController.navigate(R.id.cartFragment)
                "cart-with-product" -> {
                    prepareDemoProduct(addToCart = true)
                    navController.navigate(R.id.cartFragment)
                }
                "checkout-with-product" -> {
                    prepareDemoProduct(addToCart = true)
                    navController.navigate(R.id.checkoutFragment)
                }
                "profile" -> navController.navigate(R.id.profileFragment)
                "side-menu" -> navController.navigate(R.id.sideMenuFragment)
                "loyalty" -> navController.navigate(R.id.loyaltyQrFragment)
                "notifications" -> navController.navigate(R.id.notificationsFragment)
                "orders" -> navController.navigate(R.id.ordersFragment)
                "order" -> navController.navigate(
                    R.id.orderDetailFragment,
                    bundleOf(OrderDetailFragment.ARG_ORDER_ID to data.pathSegments.firstOrNull().orEmpty())
                )
            }
        }
    }

    private fun prepareDemoProduct(addToFavorites: Boolean = false, addToCart: Boolean = false) =
        ProductMockData.products().first().also { product ->
            ProductUiState.selectedProductId = product.id
            if (addToFavorites && !ProductUiState.favoriteManager.isFavorite(product.id)) {
                ProductUiState.favoriteManager.add(product)
            }
            if (addToCart && ProductUiState.cartManager.getItem(product.id) == null) {
                ProductUiState.cartManager.add(product)
            }
        }
}
