package com.nauk0a.data.models

import com.nauk0a.data.Item


data class FlashSaleModel(
    val category: String,
    val discount: Int,
    val image_url: String,
    val name: String,
    val price: Double
) : Item{
    override val itemId: Long = category.hashCode().toLong()
}