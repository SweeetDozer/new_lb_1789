package com.example.matule.data.auth

import com.example.matule.domain.repository.AuthRepository

/**
 * Purpose: Placeholder auth repository for Sprint 1 structure without Supabase implementation.
 * Creation date: 2026-06-03
 * Author: Mors
 */
class AuthRepositoryStub : AuthRepository {

    /**
     * Purpose: Reserved for a future backend call in GREEN or later stages.
     */
    override fun signIn(email: String, password: String) {
        TODO("Supabase auth is intentionally not implemented in the RED stage")
    }
}
