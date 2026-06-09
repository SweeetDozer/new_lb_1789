package com.example.matule.common.notification

import java.time.LocalDateTime

/**
 * Purpose: Stores notification data for Sprint 5.
 * Creation date: 2026-06-09
 * Author: Mors
 */
data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val createdAt: LocalDateTime,
    val isRead: Boolean
)

