package dev.bonch.kodik.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.solver.widgets.Snapshot
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.getField
import dev.bonch.kodik.R
import dev.bonch.kodik.activities.MainActivity
import dev.bonch.kodik.activities.SplashActivity
import dev.bonch.kodik.adapters.BannersAdapter
import dev.bonch.kodik.adapters.CoursesAdapter
import dev.bonch.kodik.models.Banner
import dev.bonch.kodik.models.Course
import kotlinx.android.synthetic.main.item_home_courses.view.*
import kotlinx.coroutines.delay
import kotlin.concurrent.thread


private lateinit var bannersAdapter: BannersAdapter
private lateinit var coursesRecycler: RecyclerView
private lateinit var bannersRecycler: RecyclerView
private lateinit var bannersLinearLayoutManager: LinearLayoutManager
private var coursesList: MutableList<Course> = Course.CoursesController().coursesList
private var bannersList: MutableList<Banner> = Banner.BannersController().bannersList
private var bundle = Bundle()

private lateinit var bottomSheet: LinearLayout
private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
private lateinit var backImage: ImageView
private lateinit var viewBack: View
private lateinit var animTrans: Animation
private lateinit var animTransReverse: Animation
private lateinit var startNowBtn: Button
private lateinit var addToMyCoursesBtn: Button
private lateinit var toast: Toast
private lateinit var textToast: TextView
private lateinit var nameCourseDescription: TextView
private lateinit var courseDescription: TextView
private var progress : Int? = null

private var animationListener = object : Animation.AnimationListener {
    override fun onAnimationRepeat(animation: Animation?) {
    }

    override fun onAnimationEnd(animation: Animation?) {
        viewBack.visibility = View.GONE
        backImage.visibility = View.GONE
        nameCourseDescription.visibility = View.GONE
    }

    override fun onAnimationStart(animation: Animation?) {
    }
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
private val adapter = object : CoursesAdapter(coursesList) {
    override fun onBindViewHolder(holder: CoursesHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.itemView.run {
            home_course_title.text = coursesList[position].courseTitle

            holder.itemView.setOnClickListener {
                bundle.putString("name_course", coursesList[position].courseTitle)

                currentCourseNumber = position

                courseDescription.text = coursesList[position].courseDescription

                nameCourseDescription.text = coursesList[position].courseTitle

                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                backImage.setImageResource(R.drawable.html_background_image)

                viewBack.isVisible = true
                backImage.isVisible = true

                backImage.startAnimation(animTransReverse)
                viewBack.startAnimation(animTransReverse)

                userCoursesRef = db.collection("users").document(email.toString())

                userCoursesRef
                    .get()
                    .addOnSuccessListener {
                        progress = it["course$currentCourseNumber"]?.toString()?.toInt() ?: -1
                    }
            }
        }
    }
}
private lateinit var userCoursesRef: DocumentReference
private lateinit var viewFragment: View
private lateinit var progr: ProgressBar
private var currentCourseNumber: Int = -1
private var email: String? = null
private lateinit var db: FirebaseFirestore

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewFragment = inflater.inflate(R.layout.fragment_home, container, false)

        bannersAdapter = BannersAdapter(bannersList)
        coursesRecycler = viewFragment.findViewById(R.id.home_main_recycler_view)
        bannersAdapter = BannersAdapter(bannersList)
        coursesRecycler.layoutManager = LinearLayoutManager(HomeFragment@ context)
        coursesRecycler.adapter = adapter
        adapter.notifyDataSetChanged()

        bannersLinearLayoutManager =
            LinearLayoutManager(container!!.context, LinearLayoutManager.HORIZONTAL, false)
        bannersLinearLayoutManager.isSmoothScrollbarEnabled = true
        bannersRecycler = viewFragment.findViewById(R.id.home_banner_recycler_view)
        bannersRecycler.layoutManager = bannersLinearLayoutManager
        bannersRecycler.adapter = bannersAdapter

        return viewFragment
    }

    private fun initView() {

        animTrans = AnimationUtils.loadAnimation(HomeFragment@ context, R.anim.translate)
        animTrans.setAnimationListener(animationListener)
        animTransReverse =
            AnimationUtils.loadAnimation(HomeFragment@ context, R.anim.translate_reverse)

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        bottomSheetBehavior.setBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {
            }

            override fun onStateChanged(p0: View, p1: Int) {
                if (p1 == 5) {
                    viewBack.startAnimation(animTrans)
                    backImage.startAnimation(animTrans)
                    nameCourseDescription.startAnimation(animTrans)

                }
            }

        })

        val toastView = layoutInflater.inflate(R.layout.toast_view, null)
        textToast = toastView.findViewById(R.id.toast_text)
        toast = Toast(LessonCardFragment@ context)
        toast.duration = Toast.LENGTH_LONG
        toast.view = toastView
    }

    private fun setClickers() {
        addToMyCoursesBtn.setOnClickListener {
            if (progress == -1) {
                userCoursesRef.update("course$currentCourseNumber", 0)
                textToast.text = getString(R.string.courses_added_to_list)
                toast.show()
            } else {
                textToast.text = "Ты уже добавил этот курс в свой список курсов:)"
                toast.show()
            }
        }

        startNowBtn.setOnClickListener {
            bundle.putParcelable("current_course", coursesList[currentCourseNumber])
            if (progress == -1) progress = 0
            bundle.putInt("progress", progress!!)
            (context as MainActivity).onLessonChairFragment(bundle)
            if (progress == -1) userCoursesRef.update("course$currentCourseNumber", 0)
        }
    }

    override fun onResume() {
        super.onResume()

        val coorLayout: CoordinatorLayout = viewFragment.findViewById(R.id.container_test)
        progr = viewFragment.findViewById(R.id.splash_progress_bar)
        if (coursesList.size !== 0) progr.visibility = View.GONE
        val viewDescription =
            layoutInflater.inflate(R.layout.item_description_course, coorLayout, true)

        viewDescription?.run {
            backImage = findViewById(R.id.background_image)
            viewBack = findViewById(R.id.background_view)
            bottomSheet = findViewById(R.id.bottom_sheet)
            startNowBtn = findViewById(R.id.start_now_button)
            addToMyCoursesBtn = findViewById(R.id.add_to_my_courses_button)
            nameCourseDescription = findViewById(R.id.course_description_name)
            courseDescription = findViewById(R.id.course_description)
        }

        initView()
        setClickers()

        email = FirebaseAuth.getInstance().currentUser?.email
        db = FirebaseFirestore.getInstance()
    }

    interface test {
        companion object {
            fun dataGot() {
                progr.visibility = View.GONE
                adapter.notifyDataSetChanged()
            }
        }
    }
}