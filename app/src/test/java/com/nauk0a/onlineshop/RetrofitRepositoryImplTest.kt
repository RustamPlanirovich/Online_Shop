package com.nauk0a.onlineshop

import com.nauk0a.onlineshop.data.models.FlashSale
import com.nauk0a.onlineshop.data.models.Latest
import com.nauk0a.onlineshop.data.remote.MyApi
import com.nauk0a.onlineshop.data.repositroy.RetrofitRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.*
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Call
import retrofit2.Response
import java.io.IOException

class RetrofitRepositoryImplTest {

    private val api: MyApi = mockk()
    private val repository = RetrofitRepositoryImpl(api)

    @Test
    fun `fetchData should return both results if both calls are successful`() = runBlocking {
        val latestCall: Call<Latest> = mockk()
        val flashSaleCall: Call<FlashSale> = mockk()

        coEvery { api.getLatest() } returns latestCall
        coEvery { api.getFlashSale() } returns flashSaleCall

        val latestResponse: Response<Latest> = mockk()
        val flashSaleResponse: Response<FlashSale> = mockk()

        every { latestCall.execute() } returns latestResponse
        every { flashSaleCall.execute() } returns flashSaleResponse

        val result = repository.fetchData()

        coVerify { api.getLatest() }
        coVerify { api.getFlashSale() }

        assertTrue(result.first.isSuccess)
        assertTrue(result.second.isSuccess)
        assertEquals(latestCall, result.first.getOrNull())
        assertEquals(flashSaleCall, result.second.getOrNull())
    }

    @Test
    fun `fetchData should return error if one of the calls fails`() = runBlocking {
        val latestCall: Call<Latest> = mockk()
        val flashSaleCall: Call<FlashSale> = mockk()

        coEvery { api.getLatest() } returns latestCall
        coEvery { api.getFlashSale() } returns flashSaleCall

        val latestResponse: Response<Latest> = mockk()
        val flashSaleException = mockk<IOException>()

        every { latestCall.execute() } returns latestResponse
        every { flashSaleCall.execute() } throws flashSaleException

        val result = repository.fetchData()

        coVerify { api.getLatest() }
        coVerify { api.getFlashSale() }

        assertTrue(result.first.isSuccess)
        assertFalse(result.second.isSuccess)
        assertEquals(latestCall, result.first.getOrNull())
        assertNull(result.second.getOrNull())
        assertEquals(flashSaleException, result.second.exceptionOrNull())
    }
}