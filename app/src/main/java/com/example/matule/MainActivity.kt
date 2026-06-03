package com.example.matule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Purpose: Main Activity that hosts Sprint 1 XML screens through Navigation Component.
 * Creation date: 2026-06-03
 * Author: Mors
 */
class MainActivity : AppCompatActivity() {

    /**
     * Purpose: Opens the layout with NavHostFragment.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
