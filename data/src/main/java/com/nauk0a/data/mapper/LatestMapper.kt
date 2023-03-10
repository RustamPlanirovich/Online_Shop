package com.nauk0a.data.mapper

import com.nauk0a.data.models.Latest
import com.nauk0a.data.models.LatestModel
import com.nauk0a.domain.models.LatestDomain
import com.nauk0a.domain.models.LatestModelDomain


fun Latest.mapToDomain() = latest?.let {
    LatestDomain(
        latest = it.map { it.mapToDomain() }
    )
}

fun LatestModel.mapToDomain() = LatestModelDomain(
    category = category, name = name, price = price, image_url = image_url
)