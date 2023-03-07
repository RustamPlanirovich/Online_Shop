package com.nauk0a.data.mapper

import com.nauk0a.data.models.FlashSale
import com.nauk0a.data.models.FlashSaleModel
import com.nauk0a.domain.models.FlashSaleDomain
import com.nauk0a.domain.models.FlashSaleModelDomain

fun FlashSale.mapToDomain() = FlashSaleDomain(
    flash_sale = flash_sale.map { it.mapToDomain() }
)

fun FlashSaleModel.mapToDomain() = FlashSaleModelDomain(
    category = category, discount = discount, image_url = image_url, name = name, price = price
)