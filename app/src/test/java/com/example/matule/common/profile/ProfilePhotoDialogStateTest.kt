package com.example.matule.common.profile

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Purpose: Tests Sprint 5 profile photo dialog state.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class ProfilePhotoDialogStateTest {

    @Test
    fun dialogContainsCameraOption() {
        assertTrue(ProfilePhotoDialogState().options.contains(ProfilePhotoSource.CAMERA))
    }

    @Test
    fun dialogContainsGalleryOption() {
        assertTrue(ProfilePhotoDialogState().options.contains(ProfilePhotoSource.GALLERY))
    }

    @Test
    fun dialogContainsKandinskyOption() {
        assertTrue(ProfilePhotoDialogState().options.contains(ProfilePhotoSource.KANDINSKY))
    }

    @Test
    fun selectedPhotoSourceIsSaved() {
        val state = ProfilePhotoDialogState()

        state.select(ProfilePhotoSource.KANDINSKY)

        assertEquals(ProfilePhotoSource.KANDINSKY, state.selectedSource)
    }
}

