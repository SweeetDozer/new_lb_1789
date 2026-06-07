package com.example.matule.presentation.catalog

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matule.R
import com.example.matule.data.local.ProductMockData
import com.example.matule.domain.model.Product
import com.example.matule.presentation.shop.ProductCardBinder
import com.example.matule.presentation.shop.ProductUiState

/**
 * Purpose: Shows Sprint 3 Catalog screen with category chips and product grid.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class CatalogFragment : Fragment() {
    private val products = ProductMockData.products()

    private lateinit var categoryRow: LinearLayout
    private lateinit var productsContainer: LinearLayout

    /**
     * Purpose: Creates Catalog XML view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_catalog, container, false)

    /**
     * Purpose: Fills categories and product grid.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryRow = view.findViewById(R.id.catalogCategoryRow)
        productsContainer = view.findViewById(R.id.catalogProductsContainer)
        view.findViewById<View>(R.id.catalogBackButton).setOnClickListener { findNavController().navigateUp() }

        renderCategories()
        renderGrid(products)
    }

    private fun renderCategories() {
        addCategoryChip(getString(R.string.shop_all), selected = false)
        addCategoryChip(getString(R.string.shop_catalog_outdoor), selected = true)
        addCategoryChip(getString(R.string.shop_catalog_tennis), selected = false)
    }

    private fun addCategoryChip(title: String, selected: Boolean) {
        val chip = TextView(requireContext())
        chip.layoutParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f).apply {
            marginEnd = 12
        }
        chip.background = requireContext().getDrawable(
            if (selected) R.drawable.bg_shop_chip_selected else R.drawable.bg_shop_chip
        )
        chip.gravity = Gravity.CENTER
        chip.text = title
        chip.setTextColor(requireContext().getColor(if (selected) R.color.matule_white else R.color.matule_text_dark))
        chip.textSize = 14f
        chip.setOnClickListener { Toast.makeText(requireContext(), title, Toast.LENGTH_SHORT).show() }
        categoryRow.addView(chip)
    }

    private fun renderGrid(items: List<Product>) {
        productsContainer.removeAllViews()
        items.chunked(GRID_COLUMNS).forEach { rowProducts ->
            val row = LinearLayout(requireContext())
            row.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = 16.dp() }
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
        } else {
            ProductUiState.favoriteManager.add(product)
        }
    }

    private fun addToCart(product: Product) {
        ProductUiState.cartManager.add(product)
        Toast.makeText(requireContext(), R.string.shop_added_to_cart, Toast.LENGTH_SHORT).show()
    }

    private fun showDetailsLater(product: Product) {
        Toast.makeText(requireContext(), product.name, Toast.LENGTH_SHORT).show()
    }

    private fun Int.dp(): Int = (this * resources.displayMetrics.density).toInt()

    private companion object {
        const val GRID_COLUMNS = 2
    }
}
