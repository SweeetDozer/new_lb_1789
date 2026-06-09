package com.example.matule.presentation.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matule.R
import com.example.matule.common.profile.ProfilePhotoDialogState
import com.example.matule.common.profile.ProfilePhotoSource
import com.example.matule.common.validation.Sprint2ValidationResult
import com.example.matule.common.profile.ProfileEditor

/**
 * Purpose: Shows Sprint 5 profile, edit profile flow, photo dialog, and loyalty card entry.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class ProfileFragment : Fragment() {
    private val editor = ProfileEditor()
    private val photoDialogState = ProfilePhotoDialogState()

    private lateinit var avatarTextView: TextView
    private lateinit var nameTextView: TextView
    private lateinit var emailTextView: TextView
    private lateinit var phoneTextView: TextView
    private lateinit var loyaltyTextView: TextView

    /**
     * Purpose: Creates Profile XML view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_profile, container, false)

    /**
     * Purpose: Binds profile data and click handlers.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindViews(view)
        renderProfile()
        view.findViewById<View>(R.id.profileBackButton).setOnClickListener { findNavController().popBackStack() }
        view.findViewById<View>(R.id.profileEditButton).setOnClickListener { showEditProfileDialog() }
        view.findViewById<View>(R.id.profileChangePhotoButton).setOnClickListener { showPhotoDialog() }
        avatarTextView.setOnClickListener { showPhotoDialog() }
        view.findViewById<View>(R.id.profileLoyaltyCard).setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_loyaltyQrFragment)
        }
    }

    private fun bindViews(view: View) {
        avatarTextView = view.findViewById(R.id.profileAvatarTextView)
        nameTextView = view.findViewById(R.id.profileNameTextView)
        emailTextView = view.findViewById(R.id.profileEmailTextView)
        phoneTextView = view.findViewById(R.id.profilePhoneTextView)
        loyaltyTextView = view.findViewById(R.id.profileLoyaltyTextView)
    }

    private fun renderProfile() {
        val profile = ProfileUiState.profile
        avatarTextView.text = profile.name.firstOrNull()?.uppercaseChar()?.toString().orEmpty()
        nameTextView.text = profile.name
        emailTextView.text = profile.email
        phoneTextView.text = profile.phone
        loyaltyTextView.text = profile.loyaltyQrData
    }

    private fun showEditProfileDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_edit_profile, null)
        val nameEditText = dialogView.findViewById<EditText>(R.id.editProfileNameEditText)
        val emailEditText = dialogView.findViewById<EditText>(R.id.editProfileEmailEditText)
        val phoneEditText = dialogView.findViewById<EditText>(R.id.editProfilePhoneEditText)
        val profile = ProfileUiState.profile

        nameEditText.setText(profile.name)
        emailEditText.setText(profile.email)
        phoneEditText.setText(profile.phone)

        AlertDialog.Builder(requireContext())
            .setTitle(R.string.profile_edit)
            .setView(dialogView)
            .setPositiveButton(R.string.profile_save, null)
            .setNegativeButton(R.string.screen_back, null)
            .create()
            .apply {
                setOnShowListener {
                    getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                        saveProfileFromDialog(nameEditText, emailEditText, phoneEditText, this)
                    }
                }
                show()
            }
    }

    private fun saveProfileFromDialog(
        nameEditText: EditText,
        emailEditText: EditText,
        phoneEditText: EditText,
        dialog: AlertDialog
    ) {
        val updatedProfile = editor.edit(
            profile = ProfileUiState.profile,
            name = nameEditText.text?.toString().orEmpty(),
            email = emailEditText.text?.toString().orEmpty(),
            phone = phoneEditText.text?.toString().orEmpty()
        )

        when (editor.save(updatedProfile)) {
            Sprint2ValidationResult.Success -> {
                ProfileUiState.profile = updatedProfile
                renderProfile()
                dialog.dismiss()
                Toast.makeText(requireContext(), R.string.profile_updated, Toast.LENGTH_SHORT).show()
            }
            is Sprint2ValidationResult.Error -> showValidationError()
        }
    }

    private fun showValidationError() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.validation_error_title)
            .setMessage(R.string.profile_data_error)
            .setPositiveButton(R.string.dialog_ok, null)
            .show()
    }

    private fun showPhotoDialog() {
        val labels = photoDialogState.options.map(::labelForSource).toTypedArray()
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.profile_change_photo)
            .setItems(labels) { _, which ->
                val source = photoDialogState.options[which]
                photoDialogState.select(source)
                ProfileUiState.profile = ProfileUiState.profile.copy(avatarSource = source)
                Toast.makeText(requireContext(), toastForSource(source), Toast.LENGTH_SHORT).show()
            }
            .show()
    }

    private fun labelForSource(source: ProfilePhotoSource): String {
        return getString(
            when (source) {
                ProfilePhotoSource.CAMERA -> R.string.profile_photo_camera
                ProfilePhotoSource.GALLERY -> R.string.profile_photo_gallery
                ProfilePhotoSource.KANDINSKY -> R.string.profile_photo_kandinsky
            }
        )
    }

    private fun toastForSource(source: ProfilePhotoSource): Int {
        return when (source) {
            ProfilePhotoSource.CAMERA -> R.string.profile_photo_camera_selected
            ProfilePhotoSource.GALLERY -> R.string.profile_photo_gallery_selected
            ProfilePhotoSource.KANDINSKY -> R.string.profile_photo_kandinsky_selected
        }
    }
}

