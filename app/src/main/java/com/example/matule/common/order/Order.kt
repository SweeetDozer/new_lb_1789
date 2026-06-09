package com.example.matule.common.order

import java.time.LocalDateTime

/**
 * Purpose: Stores order data for Sprint 5 order logic.
 * Creation date: 2026-06-09
 * Author: Mors
 */
data class Order(
    val id: String,
    val items: List<String>,
    val totalPrice: Double,
    val createdAt: LocalDateTime,
    val status: OrderStatus
)

/**
 * Purpose: Lists supported order statuses.
 */
enum class OrderStatus {
    CREATED,
    CANCELLED,
    REPEATED
}

