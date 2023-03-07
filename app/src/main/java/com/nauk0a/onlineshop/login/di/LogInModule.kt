package com.nauk0a.onlineshop.login.di

import androidx.lifecycle.ViewModel
import com.nauk0a.onlineshop.di.ViewModelKey
import com.nauk0a.onlineshop.login.LogInViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class LogInModule {

    @Binds
    @IntoMap
    @ViewModelKey(LogInViewModel::class)
    abstract fun bindViewModel(viewModel: LogInViewModel):ViewModel
}