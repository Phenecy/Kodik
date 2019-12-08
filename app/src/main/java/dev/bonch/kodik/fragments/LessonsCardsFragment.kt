package dev.bonch.kodik.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.bonch.kodik.R
import dev.bonch.kodik.activities.MainActivity
import kotlinx.android.synthetic.main.item_lesson_open_card.view.*

class LessonsCardsFragment: Fragment() {

    private lateinit var cardsRecycler: RecyclerView
    private lateinit var titleFragmentTw: TextView
    private lateinit var toast: Toast
    private lateinit var textToast: TextView

    private lateinit var nameCourse: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lessons_cards, container, false)

        titleFragmentTw = view.findViewById(R.id.title_class_card)

        val bundle: Bundle? = arguments

        if (bundle !== null) nameCourse = bundle.getString("title_pager")!!.toUpperCase()
        titleFragmentTw.text = nameCourse

        cardsRecycler = view.findViewById(R.id.recycler_class_card)
        cardsRecycler.layoutManager = GridLayoutManager(ClassCardsFragment@context, 2)
        cardsRecycler.adapter = ClassesCardAdapter()

        initView()

        return view
    }

    private fun initView() {

        val toastView = layoutInflater.inflate(R.layout.toast_view, null)
        textToast = toastView.findViewById(R.id.toast_text)
        toast = Toast(LessonCardFragment@context)
        toast.duration = Toast.LENGTH_LONG
        toast.view = toastView
    }

    inner class ClassesCardAdapter : RecyclerView.Adapter<ClassesCardAdapter.ClassCardVH>() {

        private var titlse = arrayListOf(
            "Знакомьтесь: HTML!",
            "С помощью чего в языке разметки программируется контент?",
            "Структура веб-страницы"
        )
        private var type = arrayListOf(1, 2, 3)

        inner class ClassCardVH(view: View) : RecyclerView.ViewHolder(view)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassCardVH {
            val view: View =
                if (viewType == 1 || viewType == 2)
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.item_lesson_open_card,
                        parent,
                        false
                    )
                else LayoutInflater.from(parent.context).inflate(
                    R.layout.item_lesson_locked_card,
                    parent,
                    false
                )

            if (viewType == 2) {
                val isChecked: View = view.findViewById(R.id.checked_view)
                isChecked.isVisible = false
            }

            return ClassCardVH(view)
        }

        override fun getItemViewType(position: Int): Int {
            return type[position]
        }

        override fun getItemCount(): Int {
            return titlse.size
        }

        override fun onBindViewHolder(holder: ClassCardVH, position: Int) {
            holder.itemView.run {
                if (holder.itemViewType == 3) {
                    setOnClickListener {
                        textToast.text = getString(R.string.locked_lesson)
                        toast.show()
                    }
                }
                else {
                    setOnClickListener {
                        val bundle = Bundle()
                        bundle.putString("title_pager", nameCourse)
                        bundle.putInt("number_lesson", position)
                        (context as MainActivity).onLessonCardFragment(bundle)
                    }
                }
                title_card.text = titlse[position]
            }
        }
    }
}