package com.example.matule.presentation.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matule.R
import com.example.matule.common.menu.DeauthorizationService
import com.example.matule.common.menu.LocalSession
import com.example.matule.common.menu.LogoutManager
import com.example.matule.common.menu.LogoutResult
import com.example.matule.common.menu.SideMenuItem
import com.example.matule.common.menu.SideMenuNavigator
import com.example.matule.common.menu.SwipeBackState

/**
 * Purpose: Shows Sprint 5 side menu and handles local navigation/logout.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class SideMenuFragment : Fragment() {
    private val swipeBackState = SwipeBackState()
    private val navigator = SideMenuNavigator(menuItems())
    private var touchStartX = 0f

    /**
     * Purpose: Creates Side Menu XML view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_side_menu, container, false)

    /**
     * Purpose: Renders menu items and connects click/gesture handlers.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        renderMenuItems(view.findViewById(R.id.sideMenuItemsContainer))
        view.findViewById<View>(R.id.sideMenuCloseButton).setOnClickListener { closeMenu() }
        view.findViewById<View>(R.id.sideMenuLogoutButton).setOnClickListener { logout() }
        setupSwipeClose(view)
    }

    private fun renderMenuItems(container: LinearLayout) {
        container.removeAllViews()
        menuItems().forEach { item ->
            val itemView = createMenuItemView(item)
            container.addView(itemView)
        }
    }

    private fun createMenuItemView(item: SideMenuItem): TextView {
        return TextView(requireContext()).apply {
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                54.dp()
            ).apply { bottomMargin = 10.dp() }
            gravity = android.view.Gravity.CENTER_VERTICAL
            setPadding(18.dp(), 0, 18.dp(), 0)
            text = item.title
            textSize = 17f
            setTextColor(requireContext().getColor(if (item.isEnabled) R.color.matule_text_dark else R.color.matule_text_gray))
            background = requireContext().getDrawable(
                if (item.isEnabled) R.drawable.bg_sprint5_card else R.drawable.bg_sprint5_menu_disabled
            )
            setOnClickListener { onMenuItemClick(item.id) }
        }
    }

    private fun onMenuItemClick(itemId: String) {
        when (navigator.destinationFor(itemId)) {
            DESTINATION_HOME -> findNavController().navigate(R.id.action_sideMenuFragment_to_homeFragment)
            DESTINATION_PROFILE -> findNavController().navigate(R.id.action_sideMenuFragment_to_profileFragment)
            DESTINATION_ORDERS -> findNavController().navigate(R.id.action_sideMenuFragment_to_ordersFragment)
            DESTINATION_NOTIFICATIONS -> findNavController().navigate(R.id.action_sideMenuFragment_to_notificationsFragment)
            DESTINATION_FAVORITES -> findNavController().navigate(R.id.action_sideMenuFragment_to_favoriteFragment)
            DESTINATION_CART -> findNavController().navigate(R.id.action_sideMenuFragment_to_cartFragment)
            else -> Toast.makeText(requireContext(), R.string.side_menu_settings_disabled, Toast.LENGTH_SHORT).show()
        }
    }

    private fun logout() {
        val manager = LogoutManager(
            service = object : DeauthorizationService {
                override fun deauthorize(): Boolean = true
            },
            session = LocalSession(isLoggedIn = true)
        )

        when (manager.logout()) {
            LogoutResult.LoggedOut -> findNavController().navigate(R.id.action_sideMenuFragment_to_signInFragment)
            is LogoutResult.Error -> showLogoutError()
        }
    }

    private fun showLogoutError() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.validation_error_title)
            .setMessage(R.string.side_menu_logout_error_message)
            .setPositiveButton(R.string.dialog_ok, null)
            .show()
    }

    private fun setupSwipeClose(view: View) {
        view.setOnTouchListener { _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    touchStartX = event.x
                    true
                }
                MotionEvent.ACTION_UP -> {
                    val distance = kotlin.math.abs(event.x - touchStartX)
                    if (swipeBackState.shouldClose(distance)) closeMenu()
                    true
                }
                else -> true
            }
        }
    }

    private fun closeMenu() {
        findNavController().popBackStack()
    }

    private fun Int.dp(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }

    private companion object {
        const val DESTINATION_HOME = "Home"
        const val DESTINATION_PROFILE = "Profile"
        const val DESTINATION_ORDERS = "Orders"
        const val DESTINATION_NOTIFICATIONS = "Notification"
        const val DESTINATION_FAVORITES = "Favorite"
        const val DESTINATION_CART = "Cart"
        fun menuItems(): List<SideMenuItem> = listOf(
            SideMenuItem("home", "Главная", DESTINATION_HOME, true),
            SideMenuItem("profile", "Профиль", DESTINATION_PROFILE, true),
            SideMenuItem("orders", "Заказы", DESTINATION_ORDERS, true),
            SideMenuItem("notifications", "Уведомления", DESTINATION_NOTIFICATIONS, true),
            SideMenuItem("favorites", "Избранное", DESTINATION_FAVORITES, true),
            SideMenuItem("cart", "Корзина", DESTINATION_CART, true),
            SideMenuItem("settings", "Настройки", "Settings", false)
        )
    }
}
