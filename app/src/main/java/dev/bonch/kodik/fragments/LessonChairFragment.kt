package dev.bonch.kodik.fragments

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
import dev.bonch.kodik.R
import dev.bonch.kodik.activities.MainActivity

class LessonChairFragment: Fragment() {

    private lateinit var chairLessonRec: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_lesson_chair, container, false)

        Toast.makeText(LessonChairFragment@context, "fsdfs", Toast.LENGTH_LONG).show()

        chairLessonRec = view.findViewById(R.id.lesson_list_recycler)
        chairLessonRec.layoutManager = LinearLayoutManager(container!!.context)
        chairLessonRec.adapter = ChairAdapter()

        return view
    }



    inner class ChairAdapter: RecyclerView.Adapter<ChairAdapter.ChairVH>() {

        private var test = arrayListOf("простейшие навыки", "урок 2", "урок 3", "урок 4", "урок 5", "урок 6", "урок 7", "урок 8", "урок 9")
        private var types = arrayListOf(true, true, true, true, false, false, false, false, false)

        inner class ChairVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val titleTw = itemView.findViewById<TextView>(R.id.title_lesson)
            val line = itemView.findViewById<View>(R.id.line)
        }

        override fun getItemViewType(position: Int): Int {
            return if (!types[position]) 0 else 1
        }

        override fun getItemCount(): Int {
            return test.size
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChairVH {
            val view: View
            view = if (viewType == 0) LayoutInflater.from(parent.context).inflate(
                R.layout.item_locked_lesson,
                parent,
                false)
            else LayoutInflater.from(parent.context).inflate(
                R.layout.item_current_lesson,
                parent,
                false)
            return ChairVH(view)
        }

        override fun onBindViewHolder(holder: ChairVH, position: Int) {
            holder.itemView.run {
                holder.titleTw.text = test[position]
                if (position == test.size - 1) {
                    holder.line.isVisible = false
                }
                holder.itemView.setOnClickListener{
                    if (holder.itemViewType == 0) {
                        Toast.makeText(LessonChairFragment@context, "fdfsd,", Toast.LENGTH_LONG).show()
                    }
                    else {
                        val bundle = Bundle()
                        bundle.putString("name_course", test[position])
                        (context as MainActivity).onLessonCardFragment(bundle)
                    }
                }
            }
        }
    }
}