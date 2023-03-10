package com.nauk0a.domain.models

import com.nauk0a.domain.Item

data class FlashSaleModelDomain(
    val category: String,
    val discount: Int,
    val image_url: String,
    val name: String,
    val price: Double
):java.io.Serializable,Item{
    override val itemId: Long = category.hashCode().toLong()
}