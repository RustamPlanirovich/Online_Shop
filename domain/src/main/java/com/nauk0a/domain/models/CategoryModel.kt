package com.nauk0a.domain.models

import com.nauk0a.domain.Item

data class CategoryModel(
    val categoryImage: String,
    val categoryName: String,
) : Item {
    override val itemId: Long = categoryName.hashCode().toLong()
}
