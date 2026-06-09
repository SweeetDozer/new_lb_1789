package com.example.matule.common.profile

/**
 * Purpose: Stores photo source dialog options and selected source.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class ProfilePhotoDialogState {
    val options: List<ProfilePhotoSource>
        get() = TODO("Sprint 5 GREEN")

    var selectedSource: ProfilePhotoSource? = null
        private set

    /**
     * Purpose: Saves selected photo source from dialog.
     */
    fun select(source: ProfilePhotoSource) {
        TODO("Sprint 5 GREEN")
    }
}

