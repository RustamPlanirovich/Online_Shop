package com.nauk0a.onlineshop.signin.di

import androidx.lifecycle.ViewModel
import com.nauk0a.onlineshop.di.ViewModelKey
import com.nauk0a.onlineshop.signin.SignInViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SignInModule {

    @Binds
    @IntoMap
    @ViewModelKey(SignInViewModel::class)
    abstract fun bindViewModel(viewmodel: SignInViewModel): ViewModel
}