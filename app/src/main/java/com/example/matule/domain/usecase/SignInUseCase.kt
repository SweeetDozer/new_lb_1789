package com.example.matule.domain.usecase

import com.example.matule.domain.repository.AuthRepository

/**
 * Purpose: Keeps the sign-in action in the domain layer for Sprint 1 structure.
 * Creation date: 2026-06-03
 * Author: Mors
 */
class SignInUseCase(
    private val authRepository: AuthRepository
) {

    /**
     * Purpose: Delegates sign-in to the repository interface.
     */
    fun execute(email: String, password: String) {
        authRepository.signIn(email, password)
    }
}
