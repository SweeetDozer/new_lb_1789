package com.example.matule.common.order

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Purpose: Tests Sprint 5 order swipe actions.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class OrderSwipeActionHandlerTest {

    @Test
    fun swipeRightRepeatsOrder() {
        assertEquals(OrderSwipeResult.REPEAT, OrderSwipeActionHandler().handle(OrderSwipeAction.RIGHT))
    }

    @Test
    fun swipeLeftCancelsOrder() {
        assertEquals(OrderSwipeResult.CANCEL, OrderSwipeActionHandler().handle(OrderSwipeAction.LEFT))
    }

    @Test
    fun unknownSwipeActionDoesNotCrash() {
        assertEquals(OrderSwipeResult.NONE, OrderSwipeActionHandler().handle(OrderSwipeAction.UNKNOWN))
    }
}

