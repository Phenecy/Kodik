package dev.bonch.kodik.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dev.bonch.kodik.R
import dev.bonch.kodik.models.Notification

class NotificationsAdapter(private val notificationsList: MutableList<Notification>) :
    RecyclerView.Adapter<NotificationsAdapter.NotificationsHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationsHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_notifications, parent, false)
        return NotificationsHolder(view)
    }

    override fun getItemCount(): Int = notificationsList.size

    override fun onBindViewHolder(holder: NotificationsHolder, position: Int) {
        holder.notificationText.text = notificationsList[position].notificationText
    }

    inner class NotificationsHolder(view: View) : RecyclerView.ViewHolder(view) {
        val notificationText = itemView.findViewById<TextView>(R.id.notification_text)
    }
}