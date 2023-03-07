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
import com.nauk0a.onlineshop.databinding.FlashSaleItemBinding
import com.nauk0a.domain.models.FlashSaleModelDomain

class FlashSaleAdapter :
    ListAdapter<FlashSaleModelDomain, FlashSaleAdapter.ItemViewholder>(FlashDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewholder {
        return ItemViewholder(
            FlashSaleItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemViewholder, position: Int) {
        holder.bind(getItem(position))
    }

    class ItemViewholder(private val binding: FlashSaleItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: FlashSaleModelDomain) = with(itemView) {
            //Устанавливаем категорию товара со скидкой
            binding.categoryTextView.text = item.category
            //Устанавливаем название товара со скидкой
            binding.nameTextView.text = item.name
            //Устанавливаем цену товара со скидкой
            binding.detailPriceTextView.text = "$ ${item.price}"
            //Устанавливаем размер скидки товара со скидкой
            binding.discountTextView.text = "${item.discount}% off"
            //Устанавливаем изображение товара со скидкой
            Glide
                .with(context)
                .load(item.image_url)
                .into(binding.flashImageView)

            /** При клике на элемент списка
             * - создаем объект LatestModel с данными которые хранятся в элементе
             * - создаем бандл и помещаем в него созданный объект LatestModel
             * - переходим на detailFragment и передаем созданный бандл в него
             */
            setOnClickListener {
                //Создаем объект FlashSaleModel
                val flashSaleModel = FlashSaleModelDomain(
                    category = item.category,
                    name = item.name,
                    price = item.price,
                    image_url = item.image_url,
                    discount = 0
                )
                //Создаем бандл и помещаем в него объект FlashSaleModel
                val bundle = Bundle().apply {
                    putSerializable("flash", flashSaleModel)
                }
                //переходим во фрагмент detailFragment и передаем в него bundle
                findNavController().navigate(R.id.detailFragment,bundle)
            }
        }
    }
}

class FlashDiffCallback : DiffUtil.ItemCallback<FlashSaleModelDomain>() {
    override fun areItemsTheSame(oldItem: FlashSaleModelDomain, newItem: FlashSaleModelDomain): Boolean {
        //Сравниваем поля элементов для обновления
        return oldItem.category == newItem.category &&
                oldItem.name == newItem.name &&
                oldItem.price == newItem.price &&
                oldItem.discount == newItem.discount &&
                oldItem.image_url == newItem.image_url
    }

    override fun areContentsTheSame(oldItem: FlashSaleModelDomain, newItem: FlashSaleModelDomain): Boolean {
        //Сравниваем объекты LatestModel
        return oldItem == newItem
    }
}