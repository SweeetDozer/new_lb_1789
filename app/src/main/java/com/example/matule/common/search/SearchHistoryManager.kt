package com.example.matule.common.search

/**
 * Purpose: Stores local in-memory search history for Sprint 4.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class SearchHistoryManager {
    private val queries = mutableListOf<String>()

    /**
     * Purpose: Adds query to local history.
     */
    fun add(query: String) {
        val preparedQuery = query.trim()
        if (preparedQuery.isEmpty()) {
            return
        }

        queries.remove(preparedQuery)
        queries.add(0, preparedQuery)
    }

    /**
     * Purpose: Returns saved search queries.
     */
    fun history(): List<String> {
        return queries.toList()
    }
}
