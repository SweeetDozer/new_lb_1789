package com.example.matule.common.menu

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Purpose: Tests Sprint 5 logout behavior.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class LogoutManagerTest {

    @Test
    fun successfulDeauthorizationReturnsLoggedOutState() {
        val manager = LogoutManager(FakeDeauthorizationService(true), LocalSession())

        val result = manager.logout()

        assertTrue(result is LogoutResult.LoggedOut)
    }

    @Test
    fun failedDeauthorizationReturnsErrorState() {
        val manager = LogoutManager(FakeDeauthorizationService(false), LocalSession())

        val result = manager.logout()

        assertTrue(result is LogoutResult.Error)
    }

    @Test
    fun logoutClearsLocalSessionAfterSuccess() {
        val session = LocalSession(isLoggedIn = true)
        val manager = LogoutManager(FakeDeauthorizationService(true), session)

        manager.logout()

        assertFalse(session.isLoggedIn)
    }

    private class FakeDeauthorizationService(
        private val result: Boolean
    ) : DeauthorizationService {
        override fun deauthorize(): Boolean = result
    }
}

