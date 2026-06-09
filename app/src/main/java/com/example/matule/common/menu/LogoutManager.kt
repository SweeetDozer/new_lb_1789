package com.example.matule.common.menu

/**
 * Purpose: Performs logout logic through a simple deauthorization service.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class LogoutManager(
    private val service: DeauthorizationService,
    private val session: LocalSession
) {

    /**
     * Purpose: Logs out user and clears local session after success.
     */
    fun logout(): LogoutResult {
        return if (service.deauthorize()) handleSuccess() else handleError()
    }

    private fun handleSuccess(): LogoutResult {
        clearSession()
        return LogoutResult.LoggedOut
    }

    private fun handleError(): LogoutResult {
        return LogoutResult.Error(LOGOUT_ERROR_MESSAGE)
    }

    private fun clearSession() {
        session.isLoggedIn = false
    }

    private companion object {
        const val LOGOUT_ERROR_MESSAGE = "Ошибка выхода"
    }
}

/**
 * Purpose: Provides fake-friendly deauthorization for tests.
 */
interface DeauthorizationService {
    fun deauthorize(): Boolean
}

/**
 * Purpose: Stores simple local session state for logout tests.
 */
class LocalSession(
    var isLoggedIn: Boolean = true
)

/**
 * Purpose: Represents logout result for Sprint 5.
 */
sealed class LogoutResult {
    object LoggedOut : LogoutResult()
    data class Error(val message: String) : LogoutResult()
}
