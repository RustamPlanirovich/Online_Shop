package com.nauk0a.data.models

import com.nauk0a.data.Item

data class Latest(
    val latest: List<LatestModel>?,
) : Item {
    override val itemId: Long = latest.hashCode().toLong()
}