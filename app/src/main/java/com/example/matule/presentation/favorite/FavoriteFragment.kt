package com.example.matule.presentation.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Space
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matule.R
import com.example.matule.domain.model.Product
import com.example.matule.presentation.details.DetailsFragment
import com.example.matule.presentation.shop.ProductCardBinder
import com.example.matule.presentation.shop.ProductUiState

/**
 * Purpose: Shows Sprint 3 favorite products saved in local memory.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class FavoriteFragment : Fragment() {
    private lateinit var emptyTextView: TextView
    private lateinit var productsContainer: LinearLayout

    /**
     * Purpose: Creates Favorite XML view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_favorite, container, false)

    /**
     * Purpose: Renders favorite grid and empty state.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        emptyTextView = view.findViewById(R.id.favoriteEmptyTextView)
        productsContainer = view.findViewById(R.id.favoriteProductsContainer)
        view.findViewById<View>(R.id.favoriteBackButton).setOnClickListener { findNavController().navigateUp() }
        renderFavorites()
    }

    override fun onResume() {
        super.onResume()
        if (::productsContainer.isInitialized) {
            renderFavorites()
        }
    }

    private fun renderFavorites() {
        val favorites = ProductUiState.favoriteManager.getFavorites()
        productsContainer.removeAllViews()
        emptyTextView.visibility = if (favorites.isEmpty()) View.VISIBLE else View.GONE

        favorites.chunked(GRID_COLUMNS).forEach { rowProducts ->
            val row = LinearLayout(requireContext())
            row.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = 18.dp() }
            row.orientation = LinearLayout.HORIZONTAL

            rowProducts.forEach { product ->
                val card = ProductCardBinder.createCard(row, product, ::removeFavorite, ::addToCart, ::openDetails)
                card.layoutParams = LinearLayout.LayoutParams(0, 230.dp(), 1f).apply {
                    marginStart = 8.dp()
                    marginEnd = 8.dp()
                }
                row.addView(card)
            }
            if (rowProducts.size < GRID_COLUMNS) {
                val spacer = Space(requireContext())
                spacer.layoutParams = LinearLayout.LayoutParams(0, 230.dp(), 1f).apply {
                    marginStart = 8.dp()
                    marginEnd = 8.dp()
                }
                row.addView(spacer)
            }
            productsContainer.addView(row)
        }
    }

    private fun removeFavorite(product: Product) {
        ProductUiState.favoriteManager.remove(product.id)
        Toast.makeText(requireContext(), R.string.shop_favorite_removed, Toast.LENGTH_SHORT).show()
        renderFavorites()
    }

    private fun addToCart(product: Product) {
        ProductUiState.cartManager.add(product)
        Toast.makeText(requireContext(), R.string.shop_added_to_cart, Toast.LENGTH_SHORT).show()
    }

    private fun openDetails(product: Product) {
        ProductUiState.selectedProductId = product.id
        findNavController().navigate(
            R.id.action_favoriteFragment_to_detailsFragment,
            bundleOf(DetailsFragment.ARG_PRODUCT_ID to product.id)
        )
    }

    private fun Int.dp(): Int = (this * resources.displayMetrics.density).toInt()

    private companion object {
        const val GRID_COLUMNS = 2
    }
}
