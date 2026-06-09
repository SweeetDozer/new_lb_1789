package com.example.matule.presentation.checkout

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matule.R
import com.example.matule.common.checkout.CheckoutData
import com.example.matule.common.checkout.CheckoutValidator
import com.example.matule.common.checkout.DeliveryDataSelector
import com.example.matule.common.checkout.DeliveryDataSource
import com.example.matule.common.checkout.OrderConfirmationState
import com.example.matule.common.checkout.OrderSummaryCalculator
import com.example.matule.common.validation.Sprint2ValidationResult
import com.example.matule.presentation.shop.ProductUiState

/**
 * Purpose: Shows Sprint 4 Checkout screen with contact fields, delivery, and confirmation.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class CheckoutFragment : Fragment() {
    private val cartManager = ProductUiState.cartManager
    private val checkoutValidator = CheckoutValidator()
    private val deliverySelector = DeliveryDataSelector()
    private val summaryCalculator = OrderSummaryCalculator()
    private val confirmationState = OrderConfirmationState()

    private lateinit var emailEditText: EditText
    private lateinit var phoneEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var profileAddressTextView: TextView
    private lateinit var geoAddressTextView: TextView
    private lateinit var subtotalTextView: TextView
    private lateinit var deliveryTextView: TextView
    private lateinit var totalTextView: TextView
    private var selectedSource = DeliveryDataSource.PROFILE

    /**
     * Purpose: Creates Checkout XML view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_checkout, container, false)

    /**
     * Purpose: Connects checkout inputs, delivery options, and confirm button.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        fillMockProfileData()
        renderDeliverySelection()
        renderSummary()

        view.findViewById<View>(R.id.checkoutBackButton).setOnClickListener { findNavController().navigateUp() }
        profileAddressTextView.setOnClickListener { selectDelivery(DeliveryDataSource.PROFILE) }
        geoAddressTextView.setOnClickListener { selectDelivery(DeliveryDataSource.GEOLOCATION) }
        view.findViewById<View>(R.id.checkoutConfirmButton).setOnClickListener { confirmOrder() }
    }

    private fun bindViews(view: View) {
        emailEditText = view.findViewById(R.id.checkoutEmailEditText)
        phoneEditText = view.findViewById(R.id.checkoutPhoneEditText)
        addressEditText = view.findViewById(R.id.checkoutAddressEditText)
        profileAddressTextView = view.findViewById(R.id.checkoutProfileAddressTextView)
        geoAddressTextView = view.findViewById(R.id.checkoutGeoAddressTextView)
        subtotalTextView = view.findViewById(R.id.checkoutSubtotalTextView)
        deliveryTextView = view.findViewById(R.id.checkoutDeliveryTextView)
        totalTextView = view.findViewById(R.id.checkoutTotalTextView)
    }

    private fun fillMockProfileData() {
        emailEditText.setText(PROFILE_EMAIL)
        phoneEditText.setText(PROFILE_PHONE)
        addressEditText.setText(PROFILE_ADDRESS)
    }

    private fun selectDelivery(source: DeliveryDataSource) {
        selectedSource = source
        val selection = deliverySelector.select(source, PROFILE_ADDRESS, GEOLOCATION_ADDRESS)
        addressEditText.setText(selection.address)
        renderDeliverySelection()
    }

    private fun renderDeliverySelection() {
        profileAddressTextView.background = requireContext().getDrawable(
            if (selectedSource == DeliveryDataSource.PROFILE) R.drawable.bg_shop_selected_card else R.drawable.bg_shop_card
        )
        geoAddressTextView.background = requireContext().getDrawable(
            if (selectedSource == DeliveryDataSource.GEOLOCATION) R.drawable.bg_shop_selected_card else R.drawable.bg_shop_card
        )
    }

    private fun renderSummary() {
        val subtotal = summaryCalculator.subtotal(cartManager)
        val total = summaryCalculator.total(cartManager, DELIVERY_PRICE)
        subtotalTextView.text = "${getString(R.string.cart_subtotal)}: ${price(subtotal)}"
        deliveryTextView.text = "${getString(R.string.cart_delivery)}: ${price(DELIVERY_PRICE)}"
        totalTextView.text = "${getString(R.string.cart_total)}: ${price(total)}"
    }

    private fun confirmOrder() {
        hideKeyboard()
        when (checkoutValidator.validate(currentData(), cartManager)) {
            is Sprint2ValidationResult.Success -> showSuccessDialog()
            is Sprint2ValidationResult.Error -> showErrorDialog()
        }
    }

    private fun currentData(): CheckoutData {
        return CheckoutData(
            email = emailEditText.text.toString(),
            phone = phoneEditText.text.toString(),
            address = addressEditText.text.toString(),
            deliverySource = selectedSource
        )
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.validation_error_title)
            .setMessage(R.string.checkout_validation_message)
            .setPositiveButton(R.string.dialog_ok, null)
            .show()
    }

    private fun showSuccessDialog() {
        confirmationState.confirm()
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.order_confirm_title)
            .setMessage(R.string.order_confirm_message)
            .setPositiveButton(R.string.order_return_to_shopping) { _, _ ->
                cartManager.clear()
                findNavController().navigate(
                    R.id.action_checkoutFragment_to_homeFragment
                )
            }
            .show()
    }

    private fun hideKeyboard() {
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireView().windowToken, 0)
        requireView().clearFocus()
    }

    private fun price(value: Double): String {
        return "\u20BD${value.toInt()}.00"
    }

    private companion object {
        const val PROFILE_EMAIL = "test123@mail.com"
        const val PROFILE_PHONE = "+79990000000"
        const val PROFILE_ADDRESS = "Адрес из профиля"
        // Mock geolocation address for laboratory task.
        const val GEOLOCATION_ADDRESS = "Адрес по геолокации"
        const val DELIVERY_PRICE = 60.0
    }
}
