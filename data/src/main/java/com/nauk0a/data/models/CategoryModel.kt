package com.nauk0a.data.models

import com.nauk0a.data.Item


data class CategoryModel(
    val categoryImage: String,
    val categoryName: String,
) : Item {
    override val itemId: Long = categoryImage.hashCode().toLong()
}