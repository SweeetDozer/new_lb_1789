package com.example.matule.presentation.orders

import com.example.matule.common.notification.NotificationItem
import com.example.matule.common.order.Order
import com.example.matule.common.order.OrderStatus
import java.time.LocalDateTime

/**
 * Purpose: Stores local Sprint 5 mock notifications and orders for UI screens.
 * Creation date: 2026-06-09
 * Author: Mors
 */
object Sprint5OrderUiState {
    val notifications = mutableListOf(
        NotificationItem(
            id = "n1",
            title = "Ваш заказ принят",
            message = "Мы начали обработку заказа #1001.",
            createdAt = LocalDateTime.now().minusMinutes(8),
            isRead = false
        ),
        NotificationItem(
            id = "n2",
            title = "Скидка 15%",
            message = "Сегодня действует скидка на популярные модели.",
            createdAt = LocalDateTime.now().minusHours(2),
            isRead = false
        ),
        NotificationItem(
            id = "n3",
            title = "Товар добавлен в избранное",
            message = "Nike Air Max сохранён в избранном.",
            createdAt = LocalDateTime.now().minusDays(1),
            isRead = true
        )
    )

    val orders = mutableListOf(
        Order(
            id = "1001",
            items = listOf("Nike Air Max", "Nike Runner"),
            totalPrice = 1564.0,
            createdAt = LocalDateTime.now().minusMinutes(5),
            status = OrderStatus.CREATED
        ),
        Order(
            id = "1002",
            items = listOf("Daily Comfort"),
            totalPrice = 640.0,
            createdAt = LocalDateTime.now().minusDays(1).withHour(14).withMinute(35),
            status = OrderStatus.CREATED
        ),
        Order(
            id = "1003",
            items = listOf("Green Walk"),
            totalPrice = 920.0,
            createdAt = LocalDateTime.of(2025, 3, 3, 10, 20),
            status = OrderStatus.CREATED
        )
    )

    /**
     * Purpose: Updates order status by id.
     */
    fun updateOrderStatus(orderId: String, status: OrderStatus) {
        val index = orders.indexOfFirst { order -> order.id == orderId }
        if (index != -1) {
            orders[index] = orders[index].copy(status = status)
        }
    }
}

