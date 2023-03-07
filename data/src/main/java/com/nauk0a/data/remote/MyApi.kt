package com.nauk0a.data.remote

import com.nauk0a.data.models.FlashSale
import com.nauk0a.data.models.Latest
import retrofit2.Call
import retrofit2.http.GET

interface MyApi {

    //Запрос к API для получения последних просмотренных товаров
    @GET("/v3/cc0071a1-f06e-48fa-9e90-b1c2a61eaca7")
    fun getLatest(): Call<Latest>

    //Запрос к API для получения товаров со скидкой
    @GET("/v3/a9ceeb6e-416d-4352-bde6-2203416576ac")
    fun getFlashSale(): Call<FlashSale>
}