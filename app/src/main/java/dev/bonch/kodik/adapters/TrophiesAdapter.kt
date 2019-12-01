package dev.bonch.kodik.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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
        holder.trophyTitle.text = trophiesList[position].trophyTitle
    }

    inner class TrophiesHolder(view: View) : RecyclerView.ViewHolder(view) {
        val trophyTitle = itemView.findViewById<TextView>(R.id.trophy_title)
    }

}