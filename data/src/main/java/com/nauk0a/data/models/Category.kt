package com.nauk0a.data.models

import com.nauk0a.data.Item

data class Category(
    val item: List<com.nauk0a.domain.models.CategoryModel>,
):Item{
    override val itemId: Long = item.hashCode().toLong()
}
