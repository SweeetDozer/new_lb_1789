package com.example.matule.common.menu

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Purpose: Tests pure side menu swipe-back logic.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class SwipeBackStateTest {

    @Test
    fun swipeAboveThresholdClosesSideMenu() {
        assertTrue(SwipeBackState(threshold = 120f).shouldClose(180f))
    }

    @Test
    fun swipeBelowThresholdDoesNotCloseSideMenu() {
        assertFalse(SwipeBackState(threshold = 120f).shouldClose(80f))
    }
}

