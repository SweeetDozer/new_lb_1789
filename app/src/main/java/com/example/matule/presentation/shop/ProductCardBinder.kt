package com.example.matule.presentation.shop

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.matule.R
import com.example.matule.domain.model.Product

/**
 * Purpose: Creates and fills reusable Sprint 3 product card views.
 * Creation date: 2026-06-07
 * Author: Mors
 */
object ProductCardBinder {

    /**
     * Purpose: Inflates a product card and connects favorite/add/card click actions.
     */
    fun createCard(
        parent: ViewGroup,
        product: Product,
        onFavoriteClick: (Product) -> Unit,
        onAddClick: (Product) -> Unit,
        onCardClick: (Product) -> Unit
    ): View {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_product_card, parent, false)
        bind(view, product, onFavoriteClick, onAddClick, onCardClick)
        return view
    }

    /**
     * Purpose: Binds product data to an already inflated card.
     */
    fun bind(
        view: View,
        product: Product,
        onFavoriteClick: (Product) -> Unit,
        onAddClick: (Product) -> Unit,
        onCardClick: (Product) -> Unit
    ) {
        val imageView = view.findViewById<ImageView>(R.id.productImageView)
        val favoriteButton = view.findViewById<TextView>(R.id.productFavoriteButton)
        val nameTextView = view.findViewById<TextView>(R.id.productNameTextView)
        val priceTextView = view.findViewById<TextView>(R.id.productPriceTextView)
        val addButton = view.findViewById<TextView>(R.id.productAddButton)

        imageView.setImageResource(R.drawable.img_onboard_1)
        nameTextView.text = product.name
        priceTextView.text = "₽${product.price.toInt()}.00"
        renderFavorite(favoriteButton, ProductUiState.favoriteManager.isFavorite(product.id))

        favoriteButton.setOnClickListener {
            onFavoriteClick(product)
            renderFavorite(favoriteButton, ProductUiState.favoriteManager.isFavorite(product.id))
        }
        addButton.setOnClickListener { onAddClick(product) }
        view.setOnClickListener { onCardClick(product) }
    }

    private fun renderFavorite(favoriteButton: TextView, isFavorite: Boolean) {
        favoriteButton.text = if (isFavorite) "♥" else "♡"
        favoriteButton.setTextColor(
            favoriteButton.context.getColor(
                if (isFavorite) R.color.matule_error else R.color.matule_text_dark
            )
        )
    }
}
