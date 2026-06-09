package com.example.matule.common.profile

import com.example.matule.common.validation.Sprint2ValidationResult
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Purpose: Tests Sprint 5 profile validation and editing.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class ProfileEditorTest {
    private val editor = ProfileEditor()

    @Test
    fun validProfileCanBeSaved() {
        val result = editor.save(profile())

        assertTrue(result is Sprint2ValidationResult.Success)
    }

    @Test
    fun emptyNameIsInvalid() {
        val result = editor.save(profile(name = ""))

        assertTrue(result is Sprint2ValidationResult.Error)
    }

    @Test
    fun invalidEmailIsInvalid() {
        val result = editor.save(profile(email = "bad-email"))

        assertTrue(result is Sprint2ValidationResult.Error)
    }

    @Test
    fun invalidPhoneIsInvalid() {
        val result = editor.save(profile(phone = "phone"))

        assertTrue(result is Sprint2ValidationResult.Error)
    }

    @Test
    fun editingProfileUpdatesFields() {
        val updated = editor.edit(
            profile(),
            name = "Alex",
            email = "alex123@mail.com",
            phone = "+79990001122"
        )

        assertEquals("Alex", updated.name)
        assertEquals("alex123@mail.com", updated.email)
        assertEquals("+79990001122", updated.phone)
    }

    private fun profile(
        name: String = "Mors",
        email: String = "test123@mail.com",
        phone: String = "+79990001122"
    ): Profile {
        return Profile(
            name = name,
            email = email,
            phone = phone,
            avatarSource = ProfilePhotoSource.GALLERY,
            loyaltyQrData = "MATULE-123"
        )
    }
}

