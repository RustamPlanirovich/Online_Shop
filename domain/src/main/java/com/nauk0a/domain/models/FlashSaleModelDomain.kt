package com.nauk0a.domain.models

data class FlashSaleModelDomain(
    val category: String,
    val discount: Int,
    val image_url: String,
    val name: String,
    val price: Double
):java.io.Serializable