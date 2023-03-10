package com.nauk0a.data.remote

import com.nauk0a.data.models.FlashSale
import com.nauk0a.data.models.Latest
import com.nauk0a.data.models.SearchList
import com.nauk0a.data.models.SearchWords
import retrofit2.Response
import retrofit2.http.GET

interface MyApi {
 
    //Запрос к API для получения последних просмотренных товаров
    @GET("/v3/cc0071a1-f06e-48fa-9e90-b1c2a61eaca7")
    suspend fun getLatest(): Response<Latest>

    //Запрос к API для получения товаров со скидкой
    @GET("/v3/a9ceeb6e-416d-4352-bde6-2203416576ac")
    suspend fun getFlashSale(): Response<FlashSale>

    //Запрос к API для получения списка слов при поиске
    @GET("/v3/4c9cd822-9479-4509-803d-63197e5a9e19")
    suspend fun getWordsList(): Response<SearchWords>
}