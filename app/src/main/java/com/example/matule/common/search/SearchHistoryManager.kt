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
        val preparedQuery = normalize(query)
        if (isBlankQuery(preparedQuery)) {
            return
        }

        moveToTop(preparedQuery)
    }

    /**
     * Purpose: Returns saved search queries.
     */
    fun history(): List<String> {
        return queries.toList()
    }

    private fun moveToTop(query: String) {
        queries.remove(query)
        queries.add(0, query)
    }

    private fun normalize(query: String): String {
        return query.trim()
    }

    private fun isBlankQuery(query: String): Boolean {
        return query.isEmpty()
    }
}
