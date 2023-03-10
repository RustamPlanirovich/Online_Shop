package com.nauk0a.onlineshop.home

import androidx.recyclerview.widget.DiffUtil
import com.nauk0a.domain.Item

open class BaseDiffUtilItemCallback : DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item) =
        oldItem.itemId == newItem.itemId

    override fun areContentsTheSame(oldItem: Item, newItem: Item) =
        oldItem.equals(newItem)
}