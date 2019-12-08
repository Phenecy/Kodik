package dev.bonch.kodik.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        holder.bind(notificationsList[position].notificationId)

    }

    inner class NotificationsHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(id: Long) {
            val notificationText = itemView.findViewById<TextView>(R.id.notification_text)
            val notificationImage = itemView.findViewById<ImageView>(R.id.notification_image)
            notificationText.text = notificationsList[position].notificationText
            Glide.with(itemView)
                .load(when(id){
                    0.toLong() -> "https://firebasestorage.googleapis.com/v0/b/kodik-ea6fa.appspot.com/o/notification1.png?alt=media&token=736d972f-d9be-4c40-bb05-791c4d07aed2"
                    else -> "https://firebasestorage.googleapis.com/v0/b/kodik-ea6fa.appspot.com/o/notification2.png?alt=media&token=5dfef54c-442b-4f4e-9b17-06459d079dc3"
                })
                .into(notificationImage)
        }
    }
}