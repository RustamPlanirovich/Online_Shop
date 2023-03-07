package com.nauk0a.onlineshop.di

import android.content.Context
import androidx.room.Room
import com.nauk0a.data.local.AppDatabase
import com.nauk0a.data.local.UserDao
import com.nauk0a.data.repositroy.UserRepositoryImpl
import com.nauk0a.domain.repository.UserRepository
import com.nauk0a.domain.usecase.GetUserUserDaoUseCase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDataBase(context: Context): AppDatabase {
        return Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                "shop-batabase"
            )
            .build()
    }

    @Provides
    fun provideUserDao(context: Context): UserDao {
        return provideDataBase(context).userDao()
    }

    @Provides
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepositoryImpl(userDao)
    }

    @Provides
    fun provideUserDaoUseCase(userRepository: UserRepository): GetUserUserDaoUseCase {
        return GetUserUserDaoUseCase(userRepository)
    }
}