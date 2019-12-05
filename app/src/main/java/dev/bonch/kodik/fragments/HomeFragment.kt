package dev.bonch.kodik.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.bonch.kodik.R
import dev.bonch.kodik.adapters.BannersAdapter
import dev.bonch.kodik.adapters.CoursesAdapter
import dev.bonch.kodik.models.Banner
import dev.bonch.kodik.models.Course

private lateinit var bannersAdapter: BannersAdapter
private lateinit var coursesAdapter: CoursesAdapter
private lateinit var coursesRecycler: RecyclerView
private lateinit var bannersRecycler: RecyclerView
private lateinit var coursesLinearLayoutManager: LinearLayoutManager
private lateinit var bannersLinearLayoutManager: LinearLayoutManager
private var coursesList: MutableList<Course> = Course.CoursesController().coursesList
private var bannersList: MutableList<Banner> = Banner.BannersController().bannersList

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bannersAdapter = BannersAdapter(bannersList)
        coursesAdapter = CoursesAdapter(coursesList)
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        coursesLinearLayoutManager = LinearLayoutManager(container!!.context)
        coursesLinearLayoutManager.isSmoothScrollbarEnabled = true
        coursesRecycler = view.findViewById(R.id.home_main_recycler_view)
        coursesRecycler.layoutManager = coursesLinearLayoutManager
        coursesRecycler.adapter = coursesAdapter
        bannersLinearLayoutManager = LinearLayoutManager(container!!.context, LinearLayoutManager.HORIZONTAL, false)
        bannersLinearLayoutManager.isSmoothScrollbarEnabled = true
        bannersRecycler = view.findViewById(R.id.home_banner_recycler_view)
        bannersRecycler.layoutManager = bannersLinearLayoutManager
        bannersRecycler.adapter = bannersAdapter
        return view
    }
}