package com.nauk0a.onlineshop.login.di

import com.nauk0a.onlineshop.login.LogInFragment
import dagger.Subcomponent

@Subcomponent(modules = [LogInModule::class])
interface LogInComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): LogInComponent
    }

    fun inject(fragment: LogInFragment)
}