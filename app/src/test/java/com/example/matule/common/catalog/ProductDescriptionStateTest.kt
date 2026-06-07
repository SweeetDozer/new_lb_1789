package com.example.matule.common.catalog

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Purpose: Tests collapsed and expanded product description behavior for Details screen.
 * Creation date: 2026-06-07
 * Author: Mors
 */
class ProductDescriptionStateTest {

    @Test
    fun descriptionIsCollapsedByDefault() {
        val state = ProductDescriptionState()

        assertFalse(state.isExpanded)
    }

    @Test
    fun collapsedMaxLinesIsTwo() {
        val state = ProductDescriptionState()

        assertEquals(2, state.maxLines())
    }

    @Test
    fun expandChangesStateToExpanded() {
        val state = ProductDescriptionState()

        state.expand()

        assertTrue(state.isExpanded)
    }

    @Test
    fun toggleWorks() {
        val state = ProductDescriptionState()

        state.toggle()
        assertTrue(state.isExpanded)

        state.toggle()
        assertFalse(state.isExpanded)
    }
}
