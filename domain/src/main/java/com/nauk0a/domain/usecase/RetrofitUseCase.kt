package com.nauk0a.domain.usecase

import com.nauk0a.domain.models.FlashSaleModelDomain
import com.nauk0a.domain.models.LatestModelDomain
import com.nauk0a.domain.repository.RetrofitRepository

class RetrofitUseCase(private val retrofitRepository: RetrofitRepository) {
    suspend fun getApiData(): Pair<Result<List<LatestModelDomain>?>, Result<List<FlashSaleModelDomain>?>> {
        return retrofitRepository.getLatestAndFlashData()
    }
}