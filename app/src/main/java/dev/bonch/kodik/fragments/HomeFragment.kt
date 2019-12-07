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
import dev.bonch.kodik.adapters.BannersAdapter
import dev.bonch.kodik.adapters.CoursesAdapter
import dev.bonch.kodik.models.Banner
import dev.bonch.kodik.models.Course
import kotlinx.android.synthetic.main.item_home_courses.view.*


private lateinit var bannersAdapter: BannersAdapter
private lateinit var coursesRecycler: RecyclerView
private lateinit var bannersRecycler: RecyclerView
private lateinit var bannersLinearLayoutManager: LinearLayoutManager
private var coursesList: MutableList<Course> = Course.CoursesController().coursesList
private var bannersList: MutableList<Banner> = Banner.BannersController().bannersList

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

        bannersAdapter = BannersAdapter(bannersList)
        coursesRecycler = view.findViewById(R.id.home_main_recycler_view)
        coursesRecycler.layoutManager = LinearLayoutManager(HomeFragment@context)
        coursesRecycler.adapter = adapter

        bannersLinearLayoutManager = LinearLayoutManager(container!!.context, LinearLayoutManager.HORIZONTAL, false)
        bannersLinearLayoutManager.isSmoothScrollbarEnabled = true
        bannersRecycler = view.findViewById(R.id.home_banner_recycler_view)
        bannersRecycler.layoutManager = bannersLinearLayoutManager
        bannersRecycler.adapter = bannersAdapter

        return view
    }
}