package dev.bonch.kodik.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import dev.bonch.kodik.R
import dev.bonch.kodik.activities.MainActivity
import dev.bonch.kodik.models.Course
import dev.bonch.kodik.models.Lesson

@Suppress("DEPRECATED_IDENTITY_EQUALS")
class LessonPagerCardFragment : Fragment() {

    private lateinit var lessonPager: ViewPager2
    private lateinit var titleTw: TextView
    private lateinit var toast: Toast
    private lateinit var textToast: TextView
    private var currentCourse: Course? = null
    private var lessonsList: ArrayList<Lesson> = arrayListOf()
    private var progress = 0
    private var i = 0
    private lateinit var db: FirebaseFirestore
    private var email: String? = null
    private lateinit var userCourseRef: DocumentReference
    private var position: Int = -1

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lesson_pager_card, container, false)

        titleTw = view.findViewById(R.id.title_pager)

        val bundle: Bundle? = arguments

        if (bundle !== null) {
            currentCourse = bundle.getParcelable("current_course")
            titleTw.text = currentCourse?.courseTitle
            progress = bundle.getInt("progress")
            position = bundle.getInt("position")
            lessonsList.add(currentCourse?.courseLesson!![0])

            while ((i < progress || currentCourse?.courseLesson!![i].type == "theory") && i < currentCourse?.courseLesson!!.size - 1) {
                i++
                lessonsList.add(currentCourse?.courseLesson!![i])
            }
        }

        lessonPager = view.findViewById(R.id.lesson_view_pager)

        lessonPager.adapter = LessonsAdapter()
        if (position < progress)
            lessonPager.setCurrentItem(position, false)
        else
            lessonPager.setCurrentItem(progress, false)

        initView()

        return view
    }

    private fun initView() {

        val toastView = layoutInflater.inflate(R.layout.toast_view, null)
        textToast = toastView.findViewById(R.id.toast_text)
        toast = Toast(LessonCardFragment@ context)
        toast.duration = Toast.LENGTH_LONG
        toast.view = toastView
    }

    inner class LessonsAdapter() : RecyclerView.Adapter<LessonsAdapter.PagerVH>() {

        open inner class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
            open fun bind() {}
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH {
            return if (viewType == 0) {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_lesson_card_theory, parent, false)
                TheoryViewHolder(view)
            } else {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_lesson_card_test, parent, false)
                TestViewHolder(view)
            }
        }

        override fun getItemViewType(position: Int): Int {
            return if (lessonsList[position].type == "theory") 0 else 1
        }

        override fun getItemCount(): Int {
            return lessonsList.size
        }

        override fun onBindViewHolder(holder: PagerVH, position: Int) {
            holder.bind()
        }

        inner class TheoryViewHolder(view: View) : PagerVH(view) {
            private val titleLessonTw = view.findViewById<TextView>(R.id.lesson_title)
            private val textLessonTw = view.findViewById<TextView>(R.id.lesson_text)
            private val nextLessonBtn = view.findViewById<Button>(R.id.next_lesson_button)

            override fun bind() {
                titleLessonTw.text = lessonsList[position].title
                textLessonTw.text = lessonsList[position].text

                nextLessonBtn.setOnClickListener {
                    onNextLesson()
                }
            }
        }

        inner class TestViewHolder(view: View) : PagerVH(view) {
            private val titleLessonTw = view.findViewById<TextView>(R.id.lesson_title)
            private val nextLessonBtn = view.findViewById<Button>(R.id.next_lesson_button)
            private val answerChoiceRg = view.findViewById<RadioGroup>(R.id.answer_choice)
            
            override fun bind() {
                titleLessonTw.text = lessonsList[position].title

                nextLessonBtn.setOnClickListener {
                    if (answerChoiceRg.checkedRadioButtonId == R.id.answer_3) {
                        textToast.text = getString(R.string.correct_answer)
                        toast.show()
                        i++

                        if (i == currentCourse?.courseLesson!!.size) {
                            val bundle = Bundle()
                            textToast.text = "Поздравляем! Ты закончил этот раздел!:)"
                            bundle.putParcelable("current_course", currentCourse)
                            (context as MainActivity).onLessonChairFragmentFromPager(bundle)

                        } else {
                            lessonsList.add(currentCourse?.courseLesson!![i])

                            while (currentCourse?.courseLesson!![i].type == "theory" && i < currentCourse?.courseLesson!!.size - 1) {
                                i++
                                lessonsList.add(currentCourse?.courseLesson!![i])
                            }

                            lessonPager.adapter!!.notifyDataSetChanged()
                            onNextLesson()
                        }
                    } else {
                        textToast.text = getString(R.string.wrong_answer)
                        toast.show()
                        answerChoiceRg.clearCheck()
                    }
                }
            }
        }
    }

    private fun onNextLesson() {
        val currentLesson = lessonPager.currentItem
        progress++
        userCourseRef.update("course${currentCourse!!.courseId}", progress)
        lessonPager.setCurrentItem(currentLesson + 1, true)
    }

    override fun onResume() {
        super.onResume()
        email = FirebaseAuth.getInstance().currentUser?.email
        db = FirebaseFirestore.getInstance()
        userCourseRef = db.collection("users").document(email.toString())
    }
}