package com.nauk0a.onlineshop.signin.di

import com.nauk0a.onlineshop.signin.SignInFragment
import dagger.Subcomponent

@Subcomponent(modules = [SignInModule::class])
interface SignInComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SignInComponent
    }

    fun inject(fragment: SignInFragment)
}