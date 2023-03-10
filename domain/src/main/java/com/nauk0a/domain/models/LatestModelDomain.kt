package com.nauk0a.domain.models

import com.nauk0a.domain.Item

data class LatestModelDomain(
    val category: String,
    val name: String,
    val price: Int,
    val image_url: String,
) : java.io.Serializable, Item {
    override val itemId: Long = category.hashCode().toLong()
}
