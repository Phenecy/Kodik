package dev.bonch.kodik.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
            Glide.with(holder.itemView)
                .load(when(home_course_title.text){
                    "HTML" -> "https://firebasestorage.googleapis.com/v0/b/kodik-ea6fa.appspot.com/o/course_html.png?alt=media&token=1b920a85-eb05-40a7-af57-b1e0e3f583c5"
                    "CSS" -> "https://firebasestorage.googleapis.com/v0/b/kodik-ea6fa.appspot.com/o/course_css.png?alt=media&token=498919fd-30f8-4f43-bb5b-1a6cb48cc5f2"
                    "PYTHON" -> "https://firebasestorage.googleapis.com/v0/b/kodik-ea6fa.appspot.com/o/course_python.png?alt=media&token=9a2ca777-7320-4544-b455-9c08ac4de1fc"
                    else -> "https://firebasestorage.googleapis.com/v0/b/kodik-ea6fa.appspot.com/o/course_pascal.png?alt=media&token=81490781-a768-44da-9d14-0af1af14a32f"
                })
                .into(home_course_image_view)
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