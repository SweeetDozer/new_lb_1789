package com.example.matule.presentation.placeholder

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matule.R

/**
 * Purpose: Shows temporary Sprint 5 navigation placeholders for screens from UI part 2.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class Sprint5PlaceholderFragment : Fragment() {

    /**
     * Purpose: Creates placeholder XML view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_sprint5_placeholder, container, false)

    /**
     * Purpose: Displays placeholder title and message from navigation arguments.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.placeholderBackButton).setOnClickListener {
            findNavController().popBackStack()
        }
        view.findViewById<TextView>(R.id.placeholderTitleTextView).text = arguments?.getString(ARG_TITLE).orEmpty()
        view.findViewById<TextView>(R.id.placeholderMessageTextView).text = arguments?.getString(ARG_MESSAGE).orEmpty()
    }

    private companion object {
        const val ARG_TITLE = "title"
        const val ARG_MESSAGE = "message"
    }
}

