package dev.bonch.kodik.adapters

import android.view.*
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import dev.bonch.kodik.R
import dev.bonch.kodik.models.Course

open class CoursesAdapter(private val coursesList: MutableList<Course>) :
    RecyclerView.Adapter<CoursesAdapter.CoursesHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_home_courses, parent, false)
        return CoursesHolder(view)
    }

    override fun getItemCount(): Int = coursesList.size

    override fun onBindViewHolder(holder: CoursesHolder, position: Int) {
    }

    inner class CoursesHolder(view: View) : RecyclerView.ViewHolder(view) {
        val courseTitleText = itemView.findViewById<TextView>(R.id.home_course_title)
        val courseCard = itemView.findViewById<CardView>(R.id.home_course_card)
    }

}