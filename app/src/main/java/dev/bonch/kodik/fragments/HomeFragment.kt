package dev.bonch.kodik.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.firebase.firestore.EventListener
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObjects
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
private var bundle = Bundle()


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
private val adapter = object : CoursesAdapter(coursesList) {
    override fun onBindViewHolder(holder: CoursesHolder, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.itemView.run {
            home_course_title.text = coursesList[position].courseTitle
            holder.itemView.setOnClickListener {
                bundle.putString("name_course", coursesList[position].courseTitle)

                nameCourseDescription.text = coursesList[position].courseTitle

                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
                backImage.setImageResource(R.drawable.html_background_image)

                viewDescription.isVisible = true
                backImage.isVisible = true

                backImage.startAnimation(animTransReverse)
                viewDescription.startAnimation(animTransReverse)
        }
        }
    }
}

private lateinit var bottomSheet: LinearLayout
private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
private lateinit var backImage: ImageView
private lateinit var viewDescription: View
private lateinit var textTw: TextView
private lateinit var animTrans: Animation
private lateinit var animTransReverse: Animation
private lateinit var startNowBtn: Button
private lateinit var addToMyCoursesBtn: Button
private lateinit var toast: Toast
private lateinit var textToast: TextView
private lateinit var nameCourseDescription: TextView

private var animationListener = object: Animation.AnimationListener {
    override fun onAnimationRepeat(animation: Animation?) {
    }

    override fun onAnimationEnd(animation: Animation?) {
        viewDescription.visibility = View.GONE
        backImage.visibility = View.GONE
    }

    override fun onAnimationStart(animation: Animation?) {
    }
}

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        view!!.run {
            backImage = findViewById(R.id.background_image)
            viewDescription = findViewById(R.id.background_view)
            textTw = findViewById(R.id.home_text_view)
            bottomSheet = findViewById(R.id.bottom_sheet)
            startNowBtn = findViewById(R.id.start_now_button)
            addToMyCoursesBtn = findViewById(R.id.add_to_my_courses_button)
            nameCourseDescription = findViewById(R.id.course_description_name)
        }
        bannersAdapter = BannersAdapter(bannersList)
        coursesRecycler = view.findViewById(R.id.home_main_recycler_view)
        bannersAdapter = BannersAdapter(bannersList)
        coursesRecycler.layoutManager = LinearLayoutManager(HomeFragment@context)
        coursesRecycler.adapter = adapter

        bannersLinearLayoutManager = LinearLayoutManager(container!!.context, LinearLayoutManager.HORIZONTAL, false)
        bannersLinearLayoutManager.isSmoothScrollbarEnabled = true
        bannersRecycler = view.findViewById(R.id.home_banner_recycler_view)
        bannersRecycler.layoutManager = bannersLinearLayoutManager
        bannersRecycler.adapter = bannersAdapter

        initView()
        setClickers()

        return view
    }

    private fun initView() {

        animTrans = AnimationUtils.loadAnimation(HomeFragment@context, R.anim.translate)
        animTrans.setAnimationListener(animationListener)
        animTransReverse = AnimationUtils.loadAnimation(HomeFragment@context, R.anim.translate_reverse)

        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN)

        bottomSheetBehavior.setBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, p1: Float) {
            }

            override fun onStateChanged(p0: View, p1: Int) {
                if (p1 == 5) {
                    viewDescription.startAnimation(animTrans)
                    backImage.startAnimation(animTrans)

                }
            }

        })

        val toastView = layoutInflater.inflate(R.layout.toast_view, null)
        textToast = toastView.findViewById(R.id.toast_text)
        toast = Toast(LessonCardFragment@context)
        toast.duration = Toast.LENGTH_LONG
        toast.view = toastView
    }

    private fun setClickers() {
        addToMyCoursesBtn.setOnClickListener {
            textToast.text = getString(R.string.courses_added_to_list)
            toast.show()
        }
        startNowBtn.setOnClickListener {
            (context as MainActivity).onLessonChairFragment(bundle)
        }
    }

}