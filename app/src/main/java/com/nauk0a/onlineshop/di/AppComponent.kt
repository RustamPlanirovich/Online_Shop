package com.nauk0a.onlineshop.di

import android.content.Context
import com.nauk0a.onlineshop.home.di.HomeComponent
import com.nauk0a.onlineshop.login.di.LogInComponent
import com.nauk0a.onlineshop.profile.di.ProfileComponent
import com.nauk0a.onlineshop.signin.di.SignInComponent
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        DatabaseModule::class,
        SubComponentsModule::class,
        ViewModelBuilderModule::class,
        CategoryModule::class,
        RetrofitModule::class,
        SharedPreferencesModule::class]
)
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }

    fun signInComponent(): SignInComponent.Factory
    fun logInComponent(): LogInComponent.Factory
    fun homeComponent(): HomeComponent.Factory
    fun profileComponent(): ProfileComponent.Factory

}

@Module(
    subcomponents = [
        SignInComponent::class,
        LogInComponent::class,
        HomeComponent::class,
        ProfileComponent::class]
)
object SubComponentsModule