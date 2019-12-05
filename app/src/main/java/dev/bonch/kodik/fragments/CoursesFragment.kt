package dev.bonch.kodik.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.bonch.kodik.R
import dev.bonch.kodik.activities.MainActivity
import dev.bonch.kodik.models.Course
import kotlinx.android.synthetic.main.item_home_courses.view.*

private lateinit var adapter: CoursesFragment.CoursesAdapter
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

    inner class CoursesAdapter(private val coursesList: MutableList<Course>) :
        RecyclerView.Adapter<CoursesAdapter.CoursesHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoursesHolder {
            val view =
                LayoutInflater.from(parent.context).inflate(R.layout.item_home_courses, parent, false)
            return CoursesHolder(view)
        }

        override fun getItemCount(): Int = coursesList.size

        override fun onBindViewHolder(holder: CoursesHolder, position: Int) {
            holder.itemView.run {
                home_course_title.text = coursesList[position].courseTitle
                holder.itemView.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("name_course", coursesList[position].courseTitle)

                    (context as MainActivity).onLessonChairFragmentFromMyCourses()
                }
            }
        }

        inner class CoursesHolder(view: View) : RecyclerView.ViewHolder(view) {
            val courseTitleText = itemView.findViewById<TextView>(R.id.home_course_title)
            val courseCard = itemView.findViewById<CardView>(R.id.home_course_card)
        }
    }
}