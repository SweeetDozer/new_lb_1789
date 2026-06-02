package com.example.matule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Purpose: Minimal entry Activity used only to keep Sprint 1 project structure compilable.
 * Creation date: 2026-06-03
 * Author: Mors
 */
class MainActivity : AppCompatActivity() {

    /**
     * Purpose: Opens the placeholder XML layout for the RED stage.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
