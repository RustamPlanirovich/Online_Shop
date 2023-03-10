package com.nauk0a.onlineshop.home

import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.nauk0a.domain.Item

class HomeScreenAdapter : AsyncListDifferDelegationAdapter<Item>(BaseDiffUtilItemCallback()) {
    init {
        delegatesManager.addDelegate(ShopAdapterDelegate.homeAdapterDelegate())
    }
}