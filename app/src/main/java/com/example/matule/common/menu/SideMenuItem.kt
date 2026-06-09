package com.example.matule.common.menu

/**
 * Purpose: Stores one Sprint 5 side menu item.
 * Creation date: 2026-06-09
 * Author: Mors
 */
data class SideMenuItem(
    val id: String,
    val title: String,
    val destination: String,
    val isEnabled: Boolean
)

