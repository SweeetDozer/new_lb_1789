package com.example.matule.common.search

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Purpose: Tests Sprint 4 local search history behavior.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class SearchHistoryManagerTest {

    @Test
    fun addingQueryStoresIt() {
        val manager = SearchHistoryManager()

        manager.add("nike")

        assertEquals(listOf("nike"), manager.history())
    }

    @Test
    fun emptyQueryIsNotStored() {
        val manager = SearchHistoryManager()

        manager.add("")
        manager.add("   ")

        assertTrue(manager.history().isEmpty())
    }

    @Test
    fun duplicateQueryIsNotDuplicated() {
        val manager = SearchHistoryManager()

        manager.add("nike")
        manager.add("nike")

        assertEquals(listOf("nike"), manager.history())
    }

    @Test
    fun latestQueryAppearsFirst() {
        val manager = SearchHistoryManager()

        manager.add("nike")
        manager.add("air")

        assertEquals(listOf("air", "nike"), manager.history())
    }
}
