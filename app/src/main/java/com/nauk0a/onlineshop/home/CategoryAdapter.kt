package com.nauk0a.onlineshop.home

import android.annotation.SuppressLint
import android.app.Application
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nauk0a.domain.models.CategoryModel
import com.nauk0a.onlineshop.databinding.CategoryItemBinding

class CategoryAdapter :
    ListAdapter<CategoryModel, CategoryAdapter.ItemViewholder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemViewholder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("DiscouragedApi")
        fun bind(item: CategoryModel) = with(itemView) {
            //Устанавливаем картинку категории
            Glide.with(context)
                .load(
                    resources.getIdentifier(item.categoryImage,"drawable", context.getPackageName())
                )
                .into(binding.categoryIcon)

            //Устанавливаем название категории
            binding.categoryName.text = item.categoryName

            setOnClickListener {
                // При клике ничего не происходит - просто заготовка
            }
        }
    }


}

class CategoryDiffCallback : DiffUtil.ItemCallback<CategoryModel>() {
    override fun areItemsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
        //Сравниваем поля элементов для обновления
        return oldItem.categoryName == newItem.categoryName
    }

    override fun areContentsTheSame(oldItem: CategoryModel, newItem: CategoryModel): Boolean {
        //Сравниваем объекты LatestModel
        return oldItem == newItem
    }
}