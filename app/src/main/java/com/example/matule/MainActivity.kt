package com.example.matule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import com.example.matule.presentation.orders.OrderDetailFragment

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
        openSprint5RouteIfNeeded()
    }

    /**
     * Purpose: Opens Sprint 5 screens directly for local ADB screenshot verification.
     */
    private fun openSprint5RouteIfNeeded() {
        val data = intent?.data ?: return
        val navHost = supportFragmentManager.findFragmentById(R.id.navHostFragment) as? NavHostFragment ?: return
        val navController = navHost.navController
        window.decorView.post {
            when (data.host) {
                "notifications" -> navController.navigate(R.id.notificationsFragment)
                "orders" -> navController.navigate(R.id.ordersFragment)
                "order" -> navController.navigate(
                    R.id.orderDetailFragment,
                    bundleOf(OrderDetailFragment.ARG_ORDER_ID to data.pathSegments.firstOrNull().orEmpty())
                )
            }
        }
    }
}
