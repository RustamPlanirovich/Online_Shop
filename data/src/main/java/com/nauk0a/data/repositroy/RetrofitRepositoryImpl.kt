package com.nauk0a.data.repositroy


import com.nauk0a.data.mapper.mapToDomain
import com.nauk0a.data.models.FlashSale
import com.nauk0a.data.models.Latest
import com.nauk0a.data.remote.MyApi
import com.nauk0a.domain.models.FlashSaleModelDomain
import com.nauk0a.domain.models.LatestModelDomain
import com.nauk0a.domain.repository.RetrofitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import retrofit2.Call
import javax.inject.Inject

class RetrofitRepositoryImpl @Inject constructor(private val api: MyApi) : RetrofitRepository {


    override suspend fun getLatestAndFlashData():
            Pair<Result<List<LatestModelDomain>?>, Result<List<FlashSaleModelDomain>?>> {

        val (latestResult, flashSaleResult)
                = fetchData()
        return withContext(Dispatchers.IO) {
            latestResult.map { it.execute().body()?.mapToDomain()?.latest } to
                    flashSaleResult.map { it.execute().body()?.mapToDomain()?.flash_sale }
        }
    }


    suspend fun fetchData(): Pair<Result<Call<Latest>>, Result<Call<FlashSale>>> {
        //Делаем запрос API и ждем возвращения обоих результатов
        return withContext(Dispatchers.IO) {
            //Запрос первого API
            val latestDeferred = async { api.getLatest() }
            //Запрос второго API
            val flashSaleDeferred = async { api.getFlashSale() }
            //Дожидаемся возвращения первого запроса
            val latestResult = kotlin.runCatching { latestDeferred.await() }
            //Дожидаемся возвращения второго запроса
            val flashSaleResult = kotlin.runCatching { flashSaleDeferred.await() }

            //Возвращаем пару из двух запросов к API
            latestResult to flashSaleResult
        }
    }
}