package dev.bonch.kodik.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import dev.bonch.kodik.R
import dev.bonch.kodik.activities.MainActivity
import dev.bonch.kodik.models.Course

class ClassesChairFragment: Fragment() {

    private lateinit var chairLessonRec: RecyclerView
    private lateinit var toast: Toast
    private lateinit var textToast: TextView
    private lateinit var titleTw: TextView
    private lateinit var db: FirebaseFirestore
    private var currentCourse: Course? = null

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_classes_chair, container, false)

        titleTw = view.findViewById(R.id.title_classes_chair)

        val bundle: Bundle? = arguments
        if (bundle !== null) {
            titleTw.text = "${getString(R.string.themes_of_course)} ${bundle.getString("name_course")!!.toUpperCase()}"
            currentCourse = bundle.getParcelable<Course>("current_course")
        }

        initView()

        chairLessonRec = view.findViewById(R.id.lesson_list_recycler)
        chairLessonRec.layoutManager = LinearLayoutManager(container!!.context)
        chairLessonRec.adapter = ChairAdapter(currentCourse?.courseClasses, currentCourse?.courseClassesCheck )

        return view
    }

    private fun initView() {

        val toastView = layoutInflater.inflate(R.layout.toast_view, null)
        textToast = toastView.findViewById(R.id.toast_text)
        toast = Toast(LessonCardFragment@context)
        toast.duration = Toast.LENGTH_LONG
        toast.view = toastView
    }


    inner class ChairAdapter(private val titleList: MutableList<String>?, private val checkedList: MutableList<Boolean>?): RecyclerView.Adapter<ChairAdapter.ChairVH>() {

        inner class ChairVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val titleTw = itemView.findViewById<TextView>(R.id.title_lesson)
            val line = itemView.findViewById<View>(R.id.line)
        }

        override fun getItemViewType(position: Int): Int {
            var type = -1
            if (checkedList !== null){
                type = if (checkedList[position]) 1 else 0
            }
            return type
        }

        override fun getItemCount(): Int {
            return titleList!!.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChairVH {
            val view: View = if (viewType == 0) LayoutInflater.from(parent.context).inflate(
                R.layout.item_lock_class,
                parent,
                false)
            else LayoutInflater.from(parent.context).inflate(
                R.layout.item_open_class,
                parent,
                false)
            return ChairVH(view)
        }

        override fun onBindViewHolder(holder: ChairVH, position: Int) {
            holder.itemView.run {
                holder.titleTw.text = titleList!![position]
                if (position == titleList!!.size - 1) {
                    holder.line.isVisible = false
                }
                holder.itemView.setOnClickListener{
                    if (holder.itemViewType == 0) {
                        textToast.text = getString(R.string.locked_lesson)
                        toast.show()
                    }

                    else {
                        val bundle = Bundle()
                        bundle.putParcelable("current_course", currentCourse)
                        bundle.putString("title_pager", titleList!![position])
                        (context as MainActivity).onClassCardsFragment(bundle)
                    }
                }
            }
        }
    }
}