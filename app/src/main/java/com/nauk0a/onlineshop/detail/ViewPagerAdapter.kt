package com.nauk0a.onlineshop.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nauk0a.domain.models.ImagesModel

class ViewPagerAdapter (private val data: List<ImagesModel>) : RecyclerView.Adapter<ViewPagerAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(com.nauk0a.onlineshop.R.id.image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(com.nauk0a.onlineshop.R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]

        //Устанавливаем фото в карусели
        Glide
            .with(holder.imageView.context)
            .load(item.image)
            .into(holder.imageView)

    }

    override fun getItemCount(): Int = data.size

}



