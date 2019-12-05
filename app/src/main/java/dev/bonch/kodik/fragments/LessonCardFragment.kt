package dev.bonch.kodik.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import dev.bonch.kodik.R

class LessonCardFragment: Fragment() {

    private lateinit var lessonPager: ViewPager2
    private lateinit var titleCourseTw: TextView
    private lateinit var toast: Toast
    private lateinit var textToast: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lesson_card, container, false)

        initView()

        titleCourseTw = view.findViewById(R.id.courses_text_view)

        lessonPager = view.findViewById(R.id.lesson_view_pager)
        lessonPager.adapter = LessonsAdapter()

        val bundle: Bundle? = arguments

        if (bundle !== null) {
            titleCourseTw.text = bundle.getString("name_course").toString()
        }

        return view
    }

    private fun initView() {

        val toastView = layoutInflater.inflate(R.layout.toast_view, null)
        textToast = toastView.findViewById(R.id.toast_text)
        toast = Toast(LessonCardFragment@context)
        toast.duration = Toast.LENGTH_LONG
        toast.view = toastView
    }

    inner class LessonsAdapter: RecyclerView.Adapter<LessonsAdapter.PagerVH>() {

        private var titlse = arrayListOf(
            "Знакомьтесь: HTML!",
            "С помощью чего в языке разметки программируется контент?",
            "Структура веб-страницы"
        )

        private var texts = arrayListOf(
            """Аббревиатура HTML означает “HyperText Markup Language”, или “язык гипертекстовой разметки”.

В отличие от языков программирования (в т.ч. скриптовых), в которых с помощью сценариев программируются 

функции, в языке разметки с помощью тегов программируется контент (содержимое).""",
            "",
            "Любому уважающему себя веб-дизайнеру необходимо научиться программировать на языке HTML. Более того, именно со знания HTML должно начинаться любое знакомство с премудростями веб-дизайна."
        )

        private var typesCard = arrayListOf("theory", "test", "theory")

        open inner class PagerVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
            open fun bind() {}
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerVH {
           return if (viewType == 0) {
               val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lesson_card_theory, parent, false)
               TheoryViewHolder(view)
           } else {
               val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lesson_card_test, parent, false)
               TestViewHolder(view)
           }
        }

        override fun getItemViewType(position: Int): Int {
            return if (typesCard[position] == "theory") 0 else 1
        }

        override fun getItemCount(): Int {
            return typesCard.size
        }

        override fun onBindViewHolder(holder: PagerVH, position: Int) {
            holder.bind()
        }

        inner class TheoryViewHolder(view: View): PagerVH(view) {
            private val titleLessonTw = view.findViewById<TextView>(R.id.lesson_title)
            private val textLessonTw = view.findViewById<TextView>(R.id.lesson_text)
            private val nextLessonBtn = view.findViewById<Button>(R.id.next_lesson_button)

            override fun bind() {
                titleLessonTw.text = titlse[position]
                textLessonTw.text = texts[position]

                nextLessonBtn.setOnClickListener {
                    onNextLesson()
                }
            }
        }

        inner class TestViewHolder(view: View): PagerVH(view) {
            private val titleLessonTw = view.findViewById<TextView>(R.id.lesson_title)
            private val nextLessonBtn = view.findViewById<Button>(R.id.next_lesson_button)
            private val answerChoiceRg = view.findViewById<RadioGroup>(R.id.answer_choice)


            override fun bind() {
                titleLessonTw.text = titlse[position]

                nextLessonBtn.setOnClickListener {
                    if (answerChoiceRg.checkedRadioButtonId == R.id.answer_3) {
                        textToast.text = getString(R.string.correct_answer)
                        toast.show()
                        onNextLesson()
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
        lessonPager.setCurrentItem(currentLesson + 1, true)
    }
}