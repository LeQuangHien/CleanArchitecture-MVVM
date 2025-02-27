package com.hienle.cleanarchitecture.feature.news.article

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.IOError
import com.hienle.cleanarchitecture.core.data.repository.NewsRepository
import com.hienle.cleanarchitecture.core.data.repository.OfflineFirstNewsRepository
import com.hienle.cleanarchitecture.core.model.Article
import com.hienle.cleanarchitecture.core.model.ArticleResponse
import com.hienle.cleanarchitecture.core.model.Source
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.IOException

private val articles =
    listOf(
        Article(
            source = Source(id = "", name = "Biztoc.com"),
            author = "coingape.com",
            title = "Ethereum Founder Vitalik Buterin Reacts As Elon Musk Slams Microsoft",
            description = "In a remarkable and unforeseen turn of events witnessed recently, Ethereum founder Vitalik Buterin invited Elon Musk, the founder of X Corp. and Tesla, to team up...",
            url = "https://biztoc.com/x/89324d8b669c2591",
            urlToImage = "https://c.biztoc.com/p/89324d8b669c2591/s.webp",
            publishedAt = "2024-02-27T09:34:24Z",
            content = "In a remarkable and unforeseen turn of events witnessed recently, Ethereum founder Vitalik Buterin invited Elon Musk, the founder of X Corp. and Tesla, to team up...",
        ),
        Article(
            source = Source(id = "", name = "CNN"),
            author = "businessinsider.com",
            title = "Tesla's Optimus bot is looking a little steadier on its feet. Maybe the yoga stretches are paying off",
            description = "The prototype for Optimus was first revealed in September 2022. Tesla Optimus Elon Musk shared new footage of Tesla's humanoid robot, Optimus, in an X post on Saturday...",
            url = "https://biztoc.com/x/aca260fd8da1d159",
            urlToImage = "https://c.biztoc.com/p/aca260fd8da1d159/s.webp",
            publishedAt = "2024-02-27T09:26:06Z",
            content = "The prototype for Optimus was first revealed in September 2022.Tesla OptimusElon Musk shared new footage of Tesla's humanoid robot, Optimus, in an X post on Saturday...",
        ),
        // Add more news articles here if needed
    )

private val articleResponse =
    ArticleResponse(status = "", totalResults = 2, articles = articles)

@OptIn(ExperimentalCoroutinesApi::class)
class NewsViewModelTest {
    private lateinit var newsRepository: NewsRepository

    @BeforeEach
    fun setupMocks() {
        newsRepository =
            mockk<OfflineFirstNewsRepository> {
                coEvery { getTopHeadlines(any()) } returns Either.Right(articleResponse)
            }
    }

    @Test
    fun `initially, view model returns default values`() =
        runTest {
            val viewModel =
                NewsViewModel(
                    newsRepository = newsRepository,
                    coroutineContext = coroutineContext,
                )

            val state = viewModel.viewState.value
            assertEquals(NewsViewState(), state)
        }

    @Test
    fun `when getTopHeadlines returns error, then shows error`() =
        runTest {
            coEvery { newsRepository.getTopHeadlines(any()) } returns
                Either.Left(
                    IOError(cause = IOException()),
                )

            val viewModel =
                NewsViewModel(
                    newsRepository = newsRepository,
                    coroutineContext = coroutineContext,
                )
            viewModel.fetchTopHeadlines()
            advanceUntilIdle()

            val state = viewModel.viewState.value

            assertEquals(false, state.showLoading)
            assertEquals(true, state.showError)
            assertEquals(0, state.newsItems.size)
        }

    @Test
    fun `when getTopHeadlines returns success, view state is updated accordingly`() =
        runTest {
            val viewModel =
                NewsViewModel(
                    newsRepository = newsRepository,
                    coroutineContext = coroutineContext,
                )
            viewModel.fetchTopHeadlines()
            advanceUntilIdle()

            val state = viewModel.viewState.value

            assertEquals(false, state.showLoading)
            assertEquals(false, state.showError)
            assertNotEquals(0, state.newsItems.size)
        }
}
