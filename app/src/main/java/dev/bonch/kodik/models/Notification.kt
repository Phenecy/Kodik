package dev.bonch.kodik.models

import android.widget.ImageView

data class Notification(
    val notificationId: Long,
    val notificationText: String,
    val notificationImage: ImageView?
) {
    class NotificationsController {
        val notificationsList: MutableList<Notification> = mutableListOf()

        init {
            notificationsList.add(Notification(0, "Поздравляем! Ты получил награду \"Знаток!\"", null))
        }
    }

}