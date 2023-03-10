package com.nauk0a.domain.models

import com.nauk0a.domain.Item

data class FlashSaleDomain(
    val flash_sale: List<FlashSaleModelDomain>
):Item{
    override val itemId: Long = flash_sale.hashCode().toLong()
}