package dev.bonch.kodik.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import dev.bonch.kodik.R
import dev.bonch.kodik.activities.MainActivity
import dev.bonch.kodik.adapters.CoursesAdapter
import dev.bonch.kodik.models.Course
import kotlinx.android.synthetic.main.item_home_courses.view.*


private lateinit var coursesRecycler: RecyclerView
private lateinit var linearLayoutManager: LinearLayoutManager
private lateinit var db: FirebaseFirestore

private  var courseList: MutableList<Course> = Course.MineCoursesController().myCoursesList

private val adapter = object: CoursesAdapter(courseList) {
    override fun onBindViewHolder(holder: CoursesHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.itemView.run {
            home_course_title.text = courseList[position].courseTitle

            holder.itemView.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("name_course", courseList[position].courseTitle)

                (context as MainActivity).onLessonChairFragmentFromMyCourses(bundle)
            }
        }
    }

}

class CoursesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_courses, container, false)

        linearLayoutManager = LinearLayoutManager(container!!.context)
        linearLayoutManager.isSmoothScrollbarEnabled = true

        coursesRecycler = view.findViewById(R.id.courses_mine_recycler_view)
        coursesRecycler.layoutManager = linearLayoutManager
        coursesRecycler.adapter = adapter

        return view
    }
}