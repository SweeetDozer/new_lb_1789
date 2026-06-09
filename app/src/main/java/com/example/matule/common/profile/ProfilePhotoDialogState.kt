package com.example.matule.common.profile

/**
 * Purpose: Stores photo source dialog options and selected source.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class ProfilePhotoDialogState {
    val options: List<ProfilePhotoSource>
        get() = listOf(
            ProfilePhotoSource.CAMERA,
            ProfilePhotoSource.GALLERY,
            ProfilePhotoSource.KANDINSKY
        )

    var selectedSource: ProfilePhotoSource? = null
        private set

    /**
     * Purpose: Saves selected photo source from dialog.
     */
    fun select(source: ProfilePhotoSource) {
        selectedSource = source
    }
}
