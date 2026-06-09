package com.example.matule.presentation.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matule.R
import com.example.matule.common.order.Order
import com.example.matule.common.order.OrderDateGrouper
import com.example.matule.common.order.OrderStatus
import com.example.matule.common.order.OrderSwipeAction
import com.example.matule.common.order.OrderSwipeActionHandler
import com.example.matule.common.order.OrderSwipeResult
import com.example.matule.common.order.OrderTimeFormatter
import java.time.LocalDate
import java.time.LocalDateTime

/**
 * Purpose: Shows Sprint 5 grouped order list and simple swipe actions.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class OrdersFragment : Fragment() {
    private val swipeHandler = OrderSwipeActionHandler()
    private lateinit var ordersContainer: LinearLayout
    private lateinit var emptyTextView: TextView

    /**
     * Purpose: Creates Orders XML view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_orders, container, false)

    /**
     * Purpose: Connects back button and renders grouped orders.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ordersContainer = view.findViewById(R.id.ordersContainer)
        emptyTextView = view.findViewById(R.id.ordersEmptyTextView)
        view.findViewById<View>(R.id.ordersBackButton).setOnClickListener { findNavController().popBackStack() }
        renderOrders()
    }

    private fun renderOrders() {
        ordersContainer.removeAllViews()
        emptyTextView.visibility = if (Sprint5OrderUiState.orders.isEmpty()) View.VISIBLE else View.GONE
        val groups = OrderDateGrouper(LocalDate.now()).group(Sprint5OrderUiState.orders)
        groups.forEach { (groupName, orders) ->
            ordersContainer.addView(groupHeader(groupName))
            orders.forEach { order -> ordersContainer.addView(orderCard(order)) }
        }
    }

    private fun groupHeader(title: String): TextView {
        return TextView(requireContext()).apply {
            text = title
            textSize = 20f
            setTypeface(typeface, android.graphics.Typeface.BOLD)
            setTextColor(requireContext().getColor(R.color.matule_text_dark))
            setPadding(0, 18.dp(), 0, 10.dp())
        }
    }

    private fun orderCard(order: Order): TextView {
        var startX = 0f
        val time = OrderTimeFormatter(LocalDateTime.now()).format(order.createdAt)
        return TextView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply { bottomMargin = 12.dp() }
            background = requireContext().getDrawable(R.drawable.bg_sprint5_card)
            setPadding(18.dp(), 16.dp(), 18.dp(), 16.dp())
            text = "Заказ #${order.id}\n$time • ${statusText(order.status)}\n${order.items.joinToString()}\n₽${order.totalPrice}"
            textSize = 16f
            setTextColor(requireContext().getColor(R.color.matule_text_dark))
            setOnClickListener { openDetails(order.id) }
            setOnTouchListener { _, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startX = event.x
                        true
                    }
                    MotionEvent.ACTION_UP -> {
                        val distance = event.x - startX
                        if (isSwipe(distance)) {
                            handleSwipe(order.id, distance)
                        } else {
                            openDetails(order.id)
                        }
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun handleSwipe(orderId: String, distance: Float) {
        val action = if (distance > 0) OrderSwipeAction.RIGHT else OrderSwipeAction.LEFT
        when (swipeHandler.handle(action)) {
            OrderSwipeResult.REPEAT -> repeatOrder(orderId)
            OrderSwipeResult.CANCEL -> cancelOrder(orderId)
            OrderSwipeResult.NONE -> Unit
        }
    }

    private fun isSwipe(distance: Float): Boolean {
        return kotlin.math.abs(distance) >= SWIPE_THRESHOLD
    }

    private fun openDetails(orderId: String) {
        findNavController().navigate(
            R.id.action_ordersFragment_to_orderDetailFragment,
            bundleOf(OrderDetailFragment.ARG_ORDER_ID to orderId)
        )
    }

    private fun repeatOrder(orderId: String) {
        Sprint5OrderUiState.updateOrderStatus(orderId, OrderStatus.REPEATED)
        Toast.makeText(requireContext(), R.string.order_repeated_toast, Toast.LENGTH_SHORT).show()
        renderOrders()
    }

    private fun cancelOrder(orderId: String) {
        Sprint5OrderUiState.updateOrderStatus(orderId, OrderStatus.CANCELLED)
        Toast.makeText(requireContext(), R.string.order_cancelled_toast, Toast.LENGTH_SHORT).show()
        renderOrders()
    }

    private fun statusText(status: OrderStatus): String {
        return getString(
            when (status) {
                OrderStatus.CREATED -> R.string.order_status_created
                OrderStatus.CANCELLED -> R.string.order_status_cancelled
                OrderStatus.REPEATED -> R.string.order_status_repeated
            }
        )
    }

    private fun Int.dp(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }

    private companion object {
        const val SWIPE_THRESHOLD = 120f
    }
}
