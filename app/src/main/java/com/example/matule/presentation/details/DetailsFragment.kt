package com.example.matule.presentation.details

import android.os.Bundle
import android.text.TextUtils
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matule.R
import com.example.matule.common.catalog.ProductDescriptionState
import com.example.matule.common.catalog.ProductSwiperState
import com.example.matule.data.local.ProductMockData
import com.example.matule.domain.model.Product
import com.example.matule.presentation.shop.ProductUiState

/**
 * Purpose: Shows Sprint 3 product details with description, favorite, cart, and swipe switching.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class DetailsFragment : Fragment() {
    private val products = ProductMockData.products()
    private val descriptionState = ProductDescriptionState()
    private lateinit var swiperState: ProductSwiperState
    private lateinit var gestureDetector: GestureDetector

    private lateinit var imageView: ImageView
    private lateinit var favoriteButton: TextView
    private lateinit var nameTextView: TextView
    private lateinit var priceTextView: TextView
    private lateinit var descriptionTextView: TextView
    private lateinit var moreTextView: TextView
    private lateinit var indicatorContainer: LinearLayout

    /**
     * Purpose: Creates Details XML view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_details, container, false)

    /**
     * Purpose: Connects Details controls and renders selected product.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        setupSwiper()
        setupGestures()

        view.findViewById<View>(R.id.detailsBackButton).setOnClickListener { findNavController().navigateUp() }
        favoriteButton.setOnClickListener { toggleFavorite() }
        moreTextView.setOnClickListener { toggleDescription() }
        view.findViewById<View>(R.id.detailsAddToCartButton).setOnClickListener { addToCart() }

        renderCurrentProduct()
    }

    private fun bindViews(view: View) {
        imageView = view.findViewById(R.id.detailsImageView)
        favoriteButton = view.findViewById(R.id.detailsFavoriteButton)
        nameTextView = view.findViewById(R.id.detailsNameTextView)
        priceTextView = view.findViewById(R.id.detailsPriceTextView)
        descriptionTextView = view.findViewById(R.id.detailsDescriptionTextView)
        moreTextView = view.findViewById(R.id.detailsMoreTextView)
        indicatorContainer = view.findViewById(R.id.detailsIndicatorContainer)
    }

    private fun setupSwiper() {
        swiperState = ProductSwiperState(products)
        val selectedId = arguments?.getString(ARG_PRODUCT_ID) ?: ProductUiState.selectedProductId
        val selectedIndex = products.indexOfFirst { it.id == selectedId }
        repeat(selectedIndex.coerceAtLeast(0)) {
            swiperState.next()
        }
    }

    private fun setupGestures() {
        gestureDetector = GestureDetector(requireContext(), object : GestureDetector.SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean = true

            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                val startX = e1?.x ?: return false
                val deltaX = e2.x - startX
                if (kotlin.math.abs(deltaX) < SWIPE_DISTANCE) {
                    return false
                }
                if (deltaX < 0) {
                    swiperState.next()
                } else {
                    swiperState.previous()
                }
                descriptionState.collapse()
                renderCurrentProduct()
                return true
            }
        })
        imageView.setOnTouchListener { _, event -> gestureDetector.onTouchEvent(event) }
    }

    private fun renderCurrentProduct() {
        val product = swiperState.currentProduct() ?: return
        ProductUiState.selectedProductId = product.id
        imageView.setImageResource(R.drawable.img_onboard_1)
        nameTextView.text = product.name
        priceTextView.text = "₽${product.price.toInt()}.00"
        descriptionTextView.text = product.description
        renderFavorite(product)
        renderDescription()
        renderIndicators()
    }

    private fun renderFavorite(product: Product) {
        val isFavorite = ProductUiState.favoriteManager.isFavorite(product.id)
        favoriteButton.text = if (isFavorite) "♥" else "♡"
        favoriteButton.setTextColor(requireContext().getColor(if (isFavorite) R.color.matule_error else R.color.matule_text_dark))
    }

    private fun renderDescription() {
        descriptionTextView.maxLines = descriptionState.maxLines()
        descriptionTextView.ellipsize = if (descriptionState.isExpanded) null else TextUtils.TruncateAt.END
        moreTextView.text = getString(if (descriptionState.isExpanded) R.string.shop_collapse else R.string.shop_more)
    }

    private fun renderIndicators() {
        indicatorContainer.removeAllViews()
        products.forEachIndexed { index, _ ->
            val indicator = View(requireContext())
            indicator.layoutParams = LinearLayout.LayoutParams(24.dp(), 6.dp()).apply {
                marginStart = 3.dp()
                marginEnd = 3.dp()
            }
            indicator.background = requireContext().getDrawable(
                if (swiperState.isHighlighted(index)) R.drawable.bg_shop_indicator else R.drawable.bg_shop_indicator_inactive
            )
            indicatorContainer.addView(indicator)
        }
    }

    private fun toggleFavorite() {
        val product = swiperState.currentProduct() ?: return
        if (ProductUiState.favoriteManager.isFavorite(product.id)) {
            ProductUiState.favoriteManager.remove(product.id)
            Toast.makeText(requireContext(), R.string.shop_favorite_removed, Toast.LENGTH_SHORT).show()
        } else {
            ProductUiState.favoriteManager.add(product)
            Toast.makeText(requireContext(), R.string.shop_favorite_added, Toast.LENGTH_SHORT).show()
        }
        renderFavorite(product)
    }

    private fun toggleDescription() {
        descriptionState.toggle()
        renderDescription()
    }

    private fun addToCart() {
        val product = swiperState.currentProduct() ?: return
        ProductUiState.cartManager.add(product)
        Toast.makeText(requireContext(), R.string.shop_added_to_cart, Toast.LENGTH_SHORT).show()
    }

    private fun Int.dp(): Int = (this * resources.displayMetrics.density).toInt()

    companion object {
        const val ARG_PRODUCT_ID = "product_id"
        private const val SWIPE_DISTANCE = 80
    }
}
