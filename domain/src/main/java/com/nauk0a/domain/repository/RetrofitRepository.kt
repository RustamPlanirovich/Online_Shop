package com.nauk0a.domain.repository

import com.nauk0a.domain.models.FlashSaleModelDomain
import com.nauk0a.domain.models.LatestModelDomain
import com.nauk0a.domain.models.SearchDomain

interface RetrofitRepository {

    suspend fun getLatestAndFlashData():
            Pair<Result<List<LatestModelDomain>?>,
                    Result<List<FlashSaleModelDomain>?>>

    suspend fun getSearchWords(): Result<List<String>?>
}