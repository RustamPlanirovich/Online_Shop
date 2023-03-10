package com.nauk0a.data.models

import com.nauk0a.data.Item

data class FlashSale(
    val flash_sale: List<FlashSaleModel>
):Item{
    override val itemId: Long = flash_sale.hashCode().toLong()
}