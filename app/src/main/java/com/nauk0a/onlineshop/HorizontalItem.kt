package com.nauk0a.onlineshop

import com.nauk0a.domain.Item


data class HorizontalItem(
    val name: String,
    val items: List<Item>,
) : Item {
    override val itemId: Long = name.hashCode().toLong()
}
