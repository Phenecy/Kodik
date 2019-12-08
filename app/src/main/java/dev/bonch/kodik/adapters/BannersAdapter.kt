package dev.bonch.kodik.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
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
        holder.bind()
    }

    inner class BannersHolder(view: View) : RecyclerView.ViewHolder(view){

        fun bind(){
            val bannerImageView = itemView.findViewById<ImageView>(R.id.home_banner_image_view)

            Glide.with(itemView)
                .load("https://firebasestorage.googleapis.com/v0/b/kodik-ea6fa.appspot.com/o/banner.png?alt=media&token=e0555468-2d53-4ba9-b5b9-1818b8eb4f7a")
                .into(bannerImageView)
        }
    }
}