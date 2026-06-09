package com.example.matule.common.checkout

/**
 * Purpose: Stores checkout contact and delivery fields for Sprint 4.
 * Creation date: 2026-06-09
 * Author: Mors
 */
data class CheckoutData(
    val email: String,
    val phone: String,
    val address: String,
    val deliverySource: DeliveryDataSource
)
