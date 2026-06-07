package com.example.matule.presentation.common

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

/**
 * Purpose: Provides small keyboard utility methods for XML auth screens.
 * Creation date: 2026-06-07
 * Author: Mors
 */
object KeyboardHelper {

    /**
     * Purpose: Hides soft keyboard and clears focus before dialogs or navigation.
     */
    fun hideKeyboard(fragment: Fragment) {
        val view = fragment.view ?: return
        hideKeyboard(view)
        view.clearFocus()
    }

    private fun hideKeyboard(view: View) {
        val inputMethodManager = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
