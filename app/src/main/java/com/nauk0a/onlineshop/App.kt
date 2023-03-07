package com.nauk0a.onlineshop

import android.app.Application
import com.nauk0a.onlineshop.di.AppComponent
import com.nauk0a.onlineshop.di.DaggerAppComponent


open class App : Application() {

    val appComponent: AppComponent by lazy {
        initializeComponent()
    }
    //Инициализируем основной компонент Dagger
    open fun initializeComponent(): AppComponent {
        return DaggerAppComponent.factory().create(applicationContext)
    }
}