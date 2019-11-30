package dev.bonch.kodik.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.bonch.kodik.R
import dev.bonch.kodik.adapters.CoursesAdapter
import dev.bonch.kodik.adapters.NotificationsAdapter
import dev.bonch.kodik.models.Course
import dev.bonch.kodik.models.Notification

private lateinit var adapter: NotificationsAdapter
private lateinit var coursesRecycler: RecyclerView
private lateinit var linearLayoutManager: LinearLayoutManager

private var notificationsList: MutableList<Notification> = Notification.NotificationsController().notificationsList

class NotificationsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = NotificationsAdapter(notificationsList)
        val view = inflater.inflate(R.layout.fragment_notifications, container, false)
        linearLayoutManager = LinearLayoutManager(container!!.context)
        linearLayoutManager.isSmoothScrollbarEnabled = true
        coursesRecycler = view.findViewById(R.id.notifications_recycler_view)
        coursesRecycler.layoutManager = linearLayoutManager
        coursesRecycler.adapter = adapter
        return view
    }
}