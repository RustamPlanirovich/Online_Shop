package com.nauk0a.onlineshop.home


import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.nauk0a.domain.Item

class ShopHorizonatalAdapter : AsyncListDifferDelegationAdapter<Item>(BaseDiffUtilItemCallback()) {
    init {
        delegatesManager
            .addDelegate(ShopAdapterDelegate.categoryAdapter())
            .addDelegate(ShopAdapterDelegate.latestAdapter())
            .addDelegate(ShopAdapterDelegate.flashSaleAdapter())
            .addDelegate(ShopAdapterDelegate.brandAdapter())
            .addDelegate(ShopAdapterDelegate.progressCategoryDelegate())
            .addDelegate(ShopAdapterDelegate.progressLatestDelegate())
            .addDelegate(ShopAdapterDelegate.progressFlashDelegate())
    }
}