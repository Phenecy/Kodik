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
import dev.bonch.kodik.models.Course

private lateinit var adapter: CoursesAdapter
private lateinit var coursesRecycler: RecyclerView
private lateinit var linearLayoutManager: LinearLayoutManager

private var courseList: MutableList<Course> = Course.MineCoursesController().myCoursesList

class CoursesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = CoursesAdapter(courseList)
        val view = inflater.inflate(R.layout.fragment_courses, container, false)
        linearLayoutManager = LinearLayoutManager(container!!.context)
        linearLayoutManager.isSmoothScrollbarEnabled = true
        coursesRecycler = view.findViewById(R.id.courses_mine_recycler_view)
        coursesRecycler.layoutManager = linearLayoutManager
        coursesRecycler.adapter = adapter
        return view
    }
}