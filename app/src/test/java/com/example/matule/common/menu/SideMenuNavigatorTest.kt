package com.example.matule.common.menu

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

/**
 * Purpose: Tests Sprint 5 side menu navigation rules.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class SideMenuNavigatorTest {
    private val navigator = SideMenuNavigator(
        listOf(
            SideMenuItem("home", "Home", "Home", true),
            SideMenuItem("profile", "Profile", "Profile", true),
            SideMenuItem("orders", "Orders", "Orders", true),
            SideMenuItem("notification", "Notification", "Notification", true),
            SideMenuItem("settings", "Settings", "Settings", false)
        )
    )

    @Test
    fun settingsMenuItemIsNotNavigatedTo() {
        assertNull(navigator.destinationFor("settings"))
    }

    @Test
    fun profileMenuItemNavigatesToProfile() {
        assertEquals("Profile", navigator.destinationFor("profile"))
    }

    @Test
    fun ordersMenuItemNavigatesToOrders() {
        assertEquals("Orders", navigator.destinationFor("orders"))
    }

    @Test
    fun notificationMenuItemNavigatesToNotification() {
        assertEquals("Notification", navigator.destinationFor("notification"))
    }

    @Test
    fun unknownMenuItemReturnsNull() {
        assertNull(navigator.destinationFor("unknown"))
    }
}

