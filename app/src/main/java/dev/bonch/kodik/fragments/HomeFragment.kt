package dev.bonch.kodik.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.bonch.kodik.R
import dev.bonch.kodik.activities.MainActivity
import dev.bonch.kodik.adapters.CoursesAdapter
import dev.bonch.kodik.models.Course
import kotlinx.android.synthetic.main.item_home_courses.view.*

private lateinit var coursesRecycler: RecyclerView
private lateinit var linearLayoutManager: LinearLayoutManager

private var coursesList: MutableList<Course> = Course.CoursesController().coursesList

private val adapter = object: CoursesAdapter(coursesList) {
    override fun onBindViewHolder(holder: CoursesHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.itemView.run {
            home_course_title.text = coursesList[position].courseTitle

            holder.itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("name_course", coursesList[position].courseTitle)

                (context as MainActivity).onLessonChairFragment(bundle)
            }
        }
    }
}

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        linearLayoutManager = LinearLayoutManager(container!!.context)
        linearLayoutManager.isSmoothScrollbarEnabled = true

        coursesRecycler = view.findViewById(R.id.home_main_recycler_view)
        coursesRecycler.layoutManager = linearLayoutManager
        coursesRecycler.adapter = adapter
        return view
    }
}