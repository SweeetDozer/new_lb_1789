package com.example.matule.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.matule.R

/**
 * Purpose: Shows the simple Sprint 1 Home placeholder screen.
 * Creation date: 2026-06-03
 * Author: Mors
 */
class HomeFragment : Fragment() {

    /**
     * Purpose: Creates Home placeholder XML view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_home, container, false)
}
