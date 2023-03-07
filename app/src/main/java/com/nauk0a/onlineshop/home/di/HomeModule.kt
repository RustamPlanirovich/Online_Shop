package com.nauk0a.onlineshop.home.di

import androidx.lifecycle.ViewModel
import com.nauk0a.onlineshop.di.ViewModelKey
import com.nauk0a.onlineshop.home.HomeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class HomeModule {

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindViewModel(viewModel: HomeViewModel): ViewModel
}