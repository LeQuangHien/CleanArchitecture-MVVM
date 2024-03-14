package com.hienle.cleanarchitecture.core.data.repository

import arrow.core.Either
import com.hienle.cleanarchitecture.core.model.ArticleResponse
import com.hienle.cleanarchitecture.core.network.CaNetworkDatasource
import com.hienle.cleanarchitecture.core.network.retrofit.RetrofitNetworkDatasource
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.kotlin.any

private val articleResponse =
    ArticleResponse(status = "", totalResults = 2, articles = listOf())

class OfflineFirstNewsRepositoryTest {
    private lateinit var networkDatasource: CaNetworkDatasource

    @BeforeEach
    fun setupMocks() {
        networkDatasource =
            mockk<RetrofitNetworkDatasource> {
                coEvery { getTopHeadlines(any()) } returns Either.Right(articleResponse)
            }
    }

    @Test
    fun `getTopHeadlines calls RetrofitNetworkDatasource#getTopHeadlines`() =
        runTest {
            val repository = OfflineFirstNewsRepository(networkDatasource)

            repository.getTopHeadlines(any())

            coVerify(exactly = 1) {
                networkDatasource.getTopHeadlines(any())
            }
        }
}
