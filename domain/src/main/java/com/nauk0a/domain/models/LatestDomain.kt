package com.nauk0a.domain.models

import com.nauk0a.domain.Item

data class LatestDomain(
    val latest: List<LatestModelDomain>
):Item{
    override val itemId: Long = latest.hashCode().toLong()
}