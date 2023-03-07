package com.nauk0a.domain.models

data class LatestModelDomain(
    val category: String,
    val name: String,
    val price: Int,
    val image_url: String
):java.io.Serializable
