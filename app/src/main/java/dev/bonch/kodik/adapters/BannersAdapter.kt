package dev.bonch.kodik.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import dev.bonch.kodik.R
import dev.bonch.kodik.models.Banner

class BannersAdapter(private val bannersList: MutableList<Banner>) :
    RecyclerView.Adapter<BannersAdapter.BannersHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannersHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_banners, parent, false)
        return BannersHolder(view)
    }

    override fun getItemCount(): Int = bannersList.size

    override fun onBindViewHolder(holder: BannersHolder, position: Int) {
        holder.bind(position)
    }

    inner class BannersHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(position: Int){
            val bannerCard = itemView.findViewById<CardView>(R.id.home_banner_card)
            if(position % 2 == 1) {
                bannerCard.setCardBackgroundColor(Color.parseColor("#D9DD92"))
            }
        }
    }
}