package dev.bonch.kodik.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.bonch.kodik.R
import dev.bonch.kodik.models.Trophy

class TrophiesAdapter(private val trophiesList: MutableList<Trophy>) :
    RecyclerView.Adapter<TrophiesAdapter.TrophiesHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrophiesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_trophies, parent, false)
        return TrophiesHolder(view)
    }

    override fun getItemCount(): Int = trophiesList.size

    override fun onBindViewHolder(holder: TrophiesHolder, position: Int) {
        holder.bind()
    }

    inner class TrophiesHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind() {
            val trophyTitle = itemView.findViewById<TextView>(R.id.trophy_title)
            val trophyImageView = itemView.findViewById<ImageView>(R.id.trophy_image_view)
            trophyTitle.text = trophiesList[position].trophyTitle
            Glide.with(itemView)
                .load("https://firebasestorage.googleapis.com/v0/b/kodik-ea6fa.appspot.com/o/trophy.png?alt=media&token=27b50876-6ade-4f09-8b4b-61196e1d071e")
                .into(trophyImageView)

        }
    }

}