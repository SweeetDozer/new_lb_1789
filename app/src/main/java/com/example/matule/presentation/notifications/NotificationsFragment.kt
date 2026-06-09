package com.example.matule.presentation.notifications

import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.matule.R
import com.example.matule.common.notification.NotificationManager
import com.example.matule.presentation.orders.Sprint5OrderUiState
import java.time.format.DateTimeFormatter

/**
 * Purpose: Shows Sprint 5 notifications list and read state.
 * Creation date: 2026-06-09
 * Author: Mors
 */
class NotificationsFragment : Fragment() {
    private lateinit var container: LinearLayout
    private lateinit var unreadTextView: TextView
    private lateinit var emptyTextView: TextView

    /**
     * Purpose: Creates Notifications XML view.
     */
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_notifications, container, false)

    /**
     * Purpose: Connects back button and renders notifications.
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        container = view.findViewById(R.id.notificationsContainer)
        unreadTextView = view.findViewById(R.id.notificationsUnreadTextView)
        emptyTextView = view.findViewById(R.id.notificationsEmptyTextView)
        view.findViewById<View>(R.id.notificationsBackButton).setOnClickListener { findNavController().popBackStack() }
        renderNotifications()
    }

    private fun renderNotifications() {
        container.removeAllViews()
        val manager = NotificationManager(Sprint5OrderUiState.notifications)
        unreadTextView.text = manager.unreadCount().toString()
        emptyTextView.visibility = if (Sprint5OrderUiState.notifications.isEmpty()) View.VISIBLE else View.GONE

        Sprint5OrderUiState.notifications.forEach { notification ->
            container.addView(TextView(requireContext()).apply {
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                ).apply { bottomMargin = 14.dp() }
                background = requireContext().getDrawable(R.drawable.bg_sprint5_card)
                setPadding(18.dp(), 16.dp(), 18.dp(), 16.dp())
                text = "${notification.title}\n${notification.message}\n${notification.createdAt.format(TIME_FORMATTER)}"
                textSize = 16f
                setTextColor(requireContext().getColor(if (notification.isRead) R.color.matule_text_gray else R.color.matule_text_dark))
                typeface = if (notification.isRead) Typeface.DEFAULT else Typeface.DEFAULT_BOLD
                setOnClickListener { markAsRead(notification.id) }
            })
        }
    }

    private fun markAsRead(notificationId: String) {
        val index = Sprint5OrderUiState.notifications.indexOfFirst { item -> item.id == notificationId }
        if (index != -1) {
            Sprint5OrderUiState.notifications[index] = Sprint5OrderUiState.notifications[index].copy(isRead = true)
            Toast.makeText(requireContext(), R.string.notification_read_toast, Toast.LENGTH_SHORT).show()
            renderNotifications()
        }
    }

    private fun Int.dp(): Int {
        return (this * resources.displayMetrics.density).toInt()
    }

    private companion object {
        val TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM HH:mm")
    }
}

