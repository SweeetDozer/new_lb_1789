package com.example.matule.domain.repository

/**
 * Purpose: Describes authentication operations needed by the domain layer.
 * Creation date: 2026-06-03
 * Author: Mors
 */
interface AuthRepository {

    /**
     * Purpose: Starts sign-in with email and password after local validation.
     */
    fun signIn(email: String, password: String)
}
