package com.nauk0a.onlineshop.profile.di

import androidx.lifecycle.ViewModel
import com.nauk0a.onlineshop.di.ViewModelKey
import com.nauk0a.onlineshop.profile.ProfileViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ProfileModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindViewModel(viewModel: ProfileViewModel): ViewModel
}