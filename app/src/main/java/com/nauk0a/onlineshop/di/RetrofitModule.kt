package com.nauk0a.onlineshop.di

import com.nauk0a.data.remote.MyApi
import com.nauk0a.data.repositroy.RetrofitRepositoryImpl
import com.nauk0a.domain.usecase.RetrofitUseCase
import com.nauk0a.onlineshop.BuildConfig
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module

class RetrofitModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
                        else HttpLoggingInterceptor.Level.NONE
                    })
                    .build()
            )
            .build()
    }

    @Provides
    fun provideApi(retrofit: Retrofit): MyApi {
        return retrofit.create(MyApi::class.java)
    }

    @Provides
    fun provideRetrofitRepository(api: MyApi): RetrofitRepositoryImpl {
        return RetrofitRepositoryImpl(api)
    }

    @Provides
    fun provideRetrofitUseCase(retrofitRepositoryImpl: RetrofitRepositoryImpl): RetrofitUseCase {
        return RetrofitUseCase(retrofitRepositoryImpl)
    }


    companion object {
        private const val BASE_URL = "https://run.mocky.io"
    }
}