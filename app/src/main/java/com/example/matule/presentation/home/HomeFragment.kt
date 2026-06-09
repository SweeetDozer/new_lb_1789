package com.example.matule.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matule.R
import com.example.matule.data.local.ProductMockData
import com.example.matule.domain.model.Product
import com.example.matule.presentation.details.DetailsFragment
import com.example.matule.presentation.shop.ProductCardBinder
import com.example.matule.presentation.shop.ProductUiState

/**
 * Purpose: Shows Sprint 3 Home shop screen with categories, products, and bottom navigation.
 * Creation date: 2026-06-03
 * Author: Mors
 */
class HomeFragment : Fragment() {
    private val products = ProductMockData.products()

    private lateinit var categoryRow: LinearLayout
    private lateinit var popularContainer: LinearLayout
    private lateinit var showAllPopularTextView: TextView

    /**
     * Purpose: Creates Home XML view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)

    /**
     * Purpose: Fills Home screen with mock categories and product cards.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryRow = view.findViewById(R.id.homeCategoryRow)
        popularContainer = view.findViewById(R.id.homePopularContainer)
        showAllPopularTextView = view.findViewById(R.id.showAllPopularTextView)

        renderCategories()
        renderPopularProducts()
        setupNavigation(view)
    }

    private fun renderCategories() {
        addCategoryChip(getString(R.string.shop_all), selected = true)
        addCategoryChip(getString(R.string.shop_catalog_outdoor), selected = false)
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
        chip.gravity = android.view.Gravity.CENTER
        chip.text = title
        chip.setTextColor(requireContext().getColor(if (selected) R.color.matule_white else R.color.matule_text_dark))
        chip.textSize = 16f
        chip.setOnClickListener { findNavController().navigate(R.id.action_homeFragment_to_catalogFragment) }
        categoryRow.addView(chip)
    }

    private fun renderPopularProducts() {
        popularContainer.removeAllViews()
        products.take(HOME_PRODUCT_COUNT).forEach { product ->
            val card = ProductCardBinder.createCard(
                parent = popularContainer,
                product = product,
                onFavoriteClick = ::toggleFavorite,
                onAddClick = ::addToCart,
                onCardClick = ::openDetails
            )
            card.layoutParams = LinearLayout.LayoutParams(170.dp(), 230.dp()).apply {
                marginEnd = 16.dp()
            }
            popularContainer.addView(card)
        }
    }

    private fun setupNavigation(view: View) {
        showAllPopularTextView.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_popularFragment)
        }
        view.findViewById<View>(R.id.homeFilterButton).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_catalogFragment)
        }
        view.findViewById<View>(R.id.homeSearchTextView).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }
        view.findViewById<View>(R.id.navFavoriteButton).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_favoriteFragment)
        }
        view.findViewById<View>(R.id.navCartButton).setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_cartFragment)
        }
        view.findViewById<View>(R.id.navNotificationButton).setOnClickListener {
            Toast.makeText(requireContext(), R.string.shop_notification_later, Toast.LENGTH_SHORT).show()
        }
        view.findViewById<View>(R.id.navProfileButton).setOnClickListener {
            Toast.makeText(requireContext(), R.string.shop_profile_later, Toast.LENGTH_SHORT).show()
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

    private fun openDetails(product: Product) {
        ProductUiState.selectedProductId = product.id
        findNavController().navigate(
            R.id.action_homeFragment_to_detailsFragment,
            bundleOf(DetailsFragment.ARG_PRODUCT_ID to product.id)
        )
    }

    private fun Int.dp(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }

    private companion object {
        const val HOME_PRODUCT_COUNT = 4
    }
}
