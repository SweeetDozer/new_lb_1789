package com.example.matule.presentation.orders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matule.R
import com.example.matule.common.order.Order
import com.example.matule.common.order.OrderDetailProvider
import com.example.matule.common.order.OrderStatus

/**
 * Purpose: Shows Sprint 5 order details and repeat/cancel actions.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class OrderDetailFragment : Fragment() {
    private var currentOrderId: String = ""

    /**
     * Purpose: Creates Order Detail XML view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_order_detail, container, false)

    /**
     * Purpose: Finds order by id and displays its details.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        currentOrderId = arguments?.getString(ARG_ORDER_ID).orEmpty()
        view.findViewById<View>(R.id.orderDetailBackButton).setOnClickListener { findNavController().popBackStack() }
        val order = OrderDetailProvider(Sprint5OrderUiState.orders).findById(currentOrderId)
        if (order == null) {
            showNotFoundDialog()
        } else {
            renderOrder(view, order)
        }
        view.findViewById<View>(R.id.orderRepeatButton).setOnClickListener { repeatOrder(view) }
        view.findViewById<View>(R.id.orderCancelButton).setOnClickListener { confirmCancel(view) }
    }

    private fun renderOrder(view: View, order: Order) {
        view.findViewById<TextView>(R.id.orderDetailIdTextView).text = "Заказ #${order.id}"
        view.findViewById<TextView>(R.id.orderDetailStatusTextView).text = statusText(order.status)
        val itemsContainer = view.findViewById<LinearLayout>(R.id.orderDetailItemsContainer)
        itemsContainer.removeAllViews()
        order.items.forEach { item ->
            itemsContainer.addView(TextView(requireContext()).apply {
                text = "• $item"
                textSize = 17f
                setTextColor(requireContext().getColor(R.color.matule_text_dark))
                setPadding(0, 6.dp(), 0, 6.dp())
            })
        }
        view.findViewById<TextView>(R.id.orderDetailSubtotalTextView).text = "Сумма товаров: ₽${order.totalPrice}"
        view.findViewById<TextView>(R.id.orderDetailDeliveryTextView).text = "Доставка: ₽60.0"
        view.findViewById<TextView>(R.id.orderDetailTotalTextView).text = "Итого: ₽${order.totalPrice + DELIVERY_PRICE}"
    }

    private fun repeatOrder(view: View) {
        Sprint5OrderUiState.updateOrderStatus(currentOrderId, OrderStatus.REPEATED)
        Toast.makeText(requireContext(), R.string.order_repeated_toast, Toast.LENGTH_SHORT).show()
        OrderDetailProvider(Sprint5OrderUiState.orders).findById(currentOrderId)?.let { renderOrder(view, it) }
    }

    private fun confirmCancel(view: View) {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.order_cancel_confirm_title)
            .setMessage(R.string.order_cancel_confirm_message)
            .setPositiveButton(R.string.order_cancel) { _, _ -> cancelOrder(view) }
            .setNegativeButton(R.string.screen_back, null)
            .show()
    }

    private fun cancelOrder(view: View) {
        Sprint5OrderUiState.updateOrderStatus(currentOrderId, OrderStatus.CANCELLED)
        Toast.makeText(requireContext(), R.string.order_cancelled_toast, Toast.LENGTH_SHORT).show()
        OrderDetailProvider(Sprint5OrderUiState.orders).findById(currentOrderId)?.let { renderOrder(view, it) }
    }

    private fun showNotFoundDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.validation_error_title)
            .setMessage(R.string.order_not_found)
            .setPositiveButton(R.string.dialog_ok) { _, _ -> findNavController().popBackStack() }
            .show()
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

    companion object {
        const val ARG_ORDER_ID = "order_id"
        private const val DELIVERY_PRICE = 60.0
    }
}

