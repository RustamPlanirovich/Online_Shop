package com.nauk0a.onlineshop.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nauk0a.onlineshop.R
import com.nauk0a.domain.models.LatestModelDomain
import com.nauk0a.onlineshop.databinding.LatestItemBinding

class LatestAdapter : ListAdapter<LatestModelDomain, LatestAdapter.ItemViewholder>(LatestDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            LatestItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemViewholder(private val binding: LatestItemBinding) : RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: LatestModelDomain) = with(itemView) {
            //Устанавливаем категорию последнего просмотренного товара
            binding.itemTag.text = item.category
            //Устанавливаем название последнего просмотренного товара
            binding.itemName.text = item.name
            //Устанавливаем цену последнего просмотренного товара
            binding.itemPrice.text = "$ ${item.price}"
            //Устанавливаем изображение последенего просмотренного товара
            Glide
                .with(context)
                .load(item.image_url)
                .into(binding.itemImageView)

            /** При клике на элемент списка
             * - создаем объект LatestModel с данными которые хранятся в элементе
             * - создаем бандл и помещаем в него созданный объект LatestModel
             * - переходим на detailFragment и передаем созданный бандл в него
             */
            setOnClickListener {
                //Создаем объект LatestModel
                val latestModel =  LatestModelDomain(
                    category = item.category,
                    name = item.name,
                    price = item.price,
                    image_url = item.image_url
                )
                //Создаем бандл и помещаем в него объект LatestModel
                val bundle = Bundle().apply {
                    putSerializable("latest", latestModel)
                }
                //переходим во фрагмент detailFragment и передаем в него bundle
                findNavController().navigate(R.id.detailFragment,bundle)
            }
        }
    }
}

class LatestDiffCallback : DiffUtil.ItemCallback<LatestModelDomain>() {
    override fun areItemsTheSame(oldItem: LatestModelDomain, newItem: LatestModelDomain): Boolean {
        //Сравниваем поля элементов для обновления
        return oldItem.category == newItem.category &&
                oldItem.name == newItem.name &&
                oldItem.price == newItem.price &&
                oldItem.image_url == newItem.image_url
    }

    override fun areContentsTheSame(oldItem: LatestModelDomain, newItem: LatestModelDomain): Boolean {
        //Сравниваем объекты LatestModel
        return oldItem == newItem
    }
}