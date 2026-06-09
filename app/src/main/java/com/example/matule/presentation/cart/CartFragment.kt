package com.example.matule.presentation.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matule.R
import com.example.matule.common.cart.CartSwipeAction
import com.example.matule.common.cart.CartSwipeActionHandler
import com.example.matule.common.checkout.OrderSummaryCalculator
import com.example.matule.domain.model.CartItem
import com.example.matule.presentation.shop.ProductUiState

/**
 * Purpose: Shows Sprint 4 My Cart screen with quantities, summary, and swipe actions.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class CartFragment : Fragment() {
    private val cartManager = ProductUiState.cartManager
    private val summaryCalculator = OrderSummaryCalculator()
    private val swipeActionHandler = CartSwipeActionHandler(cartManager)

    private lateinit var cartScrollView: ScrollView
    private lateinit var cartItemsContainer: LinearLayout
    private lateinit var emptyTextView: TextView
    private lateinit var summaryContainer: View
    private lateinit var subtotalTextView: TextView
    private lateinit var deliveryTextView: TextView
    private lateinit var totalTextView: TextView

    /**
     * Purpose: Creates My Cart XML view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_cart, container, false)

    /**
     * Purpose: Connects cart list, buttons, and swipe actions.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)

        view.findViewById<View>(R.id.cartBackButton).setOnClickListener { findNavController().navigateUp() }
        view.findViewById<View>(R.id.cartCheckoutButton).setOnClickListener {
            findNavController().navigate(R.id.action_cartFragment_to_checkoutFragment)
        }
        renderCart()
    }

    private fun bindViews(view: View) {
        cartScrollView = view.findViewById(R.id.cartScrollView)
        cartItemsContainer = view.findViewById(R.id.cartItemsContainer)
        emptyTextView = view.findViewById(R.id.cartEmptyTextView)
        summaryContainer = view.findViewById(R.id.cartSummaryContainer)
        subtotalTextView = view.findViewById(R.id.cartSubtotalTextView)
        deliveryTextView = view.findViewById(R.id.cartDeliveryTextView)
        totalTextView = view.findViewById(R.id.cartTotalTextView)
    }

    private fun handleSwipe(productId: String, action: CartSwipeAction, toastMessage: Int) {
        swipeActionHandler.handle(productId, action)
        Toast.makeText(requireContext(), toastMessage, Toast.LENGTH_SHORT).show()
        renderCart()
    }

    private fun increaseQuantity(productId: String) {
        cartManager.increase(productId)
        renderCart()
    }

    private fun decreaseQuantity(productId: String) {
        cartManager.decrease(productId)
        renderCart()
    }

    private fun deleteItem(productId: String) {
        cartManager.remove(productId)
        Toast.makeText(requireContext(), R.string.cart_item_deleted, Toast.LENGTH_SHORT).show()
        renderCart()
    }

    private fun renderCart() {
        val items = cartManager.items()
        val hasItems = items.isNotEmpty()
        emptyTextView.visibility = if (hasItems) View.GONE else View.VISIBLE
        cartScrollView.visibility = if (hasItems) View.VISIBLE else View.GONE
        summaryContainer.visibility = if (hasItems) View.VISIBLE else View.GONE
        renderCartItems(items)

        val subtotal = summaryCalculator.subtotal(cartManager)
        subtotalTextView.text = price(subtotal)
        deliveryTextView.text = price(DELIVERY_PRICE)
        totalTextView.text = price(summaryCalculator.total(cartManager, DELIVERY_PRICE))
    }

    private fun renderCartItems(items: List<CartItem>) {
        cartItemsContainer.removeAllViews()
        items.forEach { item ->
            cartItemsContainer.addView(createCartItemView(item))
        }
    }

    private fun createCartItemView(item: CartItem): View {
        val row = layoutInflater.inflate(R.layout.item_cart_product, cartItemsContainer, false)
        val productId = item.product.id
        row.findViewById<ImageView>(R.id.cartProductImageView).setImageResource(R.drawable.img_onboard_1)
        row.findViewById<TextView>(R.id.cartProductNameTextView).text = item.product.name
        row.findViewById<TextView>(R.id.cartProductPriceTextView).text = price(item.product.price)
        row.findViewById<TextView>(R.id.cartQuantityTextView).text = item.quantity.toString()
        row.findViewById<View>(R.id.cartPlusButton).setOnClickListener { increaseQuantity(productId) }
        row.findViewById<View>(R.id.cartMinusButton).setOnClickListener { decreaseQuantity(productId) }
        row.findViewById<View>(R.id.cartDeleteButton).setOnClickListener { deleteItem(productId) }
        // TODO: Replace manual row swipe with RecyclerView ItemTouchHelper when dependency is available offline.
        row.setOnTouchListener(createSwipeListener(productId))
        return row
    }

    private fun createSwipeListener(productId: String): View.OnTouchListener {
        var downX = 0f
        return View.OnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    downX = event.x
                    true
                }
                MotionEvent.ACTION_UP -> {
                    val deltaX = event.x - downX
                    when {
                        deltaX > SWIPE_DISTANCE -> handleSwipe(
                            productId,
                            CartSwipeAction.RIGHT,
                            R.string.cart_quantity_increased
                        )
                        deltaX < -SWIPE_DISTANCE -> handleSwipe(
                            productId,
                            CartSwipeAction.LEFT,
                            R.string.cart_item_deleted
                        )
                    }
                    true
                }
                else -> false
            }
        }
    }

    private fun price(value: Double): String {
        return "\u20BD${value.toInt()}.00"
    }

    private companion object {
        const val DELIVERY_PRICE = 60.0
        const val SWIPE_DISTANCE = 120
    }
}
