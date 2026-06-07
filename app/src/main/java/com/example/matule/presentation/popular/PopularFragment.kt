package com.example.matule.presentation.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matule.R
import com.example.matule.data.local.ProductMockData
import com.example.matule.domain.model.Product
import com.example.matule.presentation.shop.ProductCardBinder
import com.example.matule.presentation.shop.ProductUiState

/**
 * Purpose: Shows Sprint 3 Popular products screen with reusable product cards.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class PopularFragment : Fragment() {
    private val products = ProductMockData.products()

    private lateinit var productsContainer: LinearLayout

    /**
     * Purpose: Creates Popular XML view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_popular, container, false)

    /**
     * Purpose: Fills Popular screen product grid and simple actions.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        productsContainer = view.findViewById(R.id.popularProductsContainer)
        view.findViewById<View>(R.id.popularBackButton).setOnClickListener { findNavController().navigateUp() }
        view.findViewById<View>(R.id.popularFilterButton).setOnClickListener {
            Toast.makeText(requireContext(), R.string.shop_filter, Toast.LENGTH_SHORT).show()
        }
        renderGrid()
    }

    private fun renderGrid() {
        productsContainer.removeAllViews()
        products.chunked(GRID_COLUMNS).forEach { rowProducts ->
            val row = LinearLayout(requireContext())
            row.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = 18.dp() }
            row.orientation = LinearLayout.HORIZONTAL

            rowProducts.forEach { product ->
                val card = ProductCardBinder.createCard(row, product, ::toggleFavorite, ::addToCart, ::showDetailsLater)
                card.layoutParams = LinearLayout.LayoutParams(0, 230.dp(), 1f).apply {
                    marginEnd = 8.dp()
                    marginStart = 8.dp()
                }
                row.addView(card)
            }
            productsContainer.addView(row)
        }
    }

    private fun toggleFavorite(product: Product) {
        if (ProductUiState.favoriteManager.isFavorite(product.id)) {
            ProductUiState.favoriteManager.remove(product.id)
            Toast.makeText(requireContext(), R.string.shop_favorite_removed, Toast.LENGTH_SHORT).show()
        } else {
            ProductUiState.favoriteManager.add(product)
            Toast.makeText(requireContext(), R.string.shop_favorite_added, Toast.LENGTH_SHORT).show()
        }
    }

    private fun addToCart(product: Product) {
        ProductUiState.cartManager.add(product)
        Toast.makeText(requireContext(), R.string.shop_added_to_cart, Toast.LENGTH_SHORT).show()
    }

    private fun showDetailsLater(product: Product) {
        Toast.makeText(requireContext(), "${product.name}: ${getString(R.string.shop_details_later)}", Toast.LENGTH_SHORT).show()
    }

    private fun Int.dp(): Int = (this * resources.displayMetrics.density).toInt()

    private companion object {
        const val GRID_COLUMNS = 2
    }
}
