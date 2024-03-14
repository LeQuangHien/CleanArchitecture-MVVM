package com.hienle.cleanarchitecture.feature.news.article

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.hienle.cleanarchitecture.core.model.Source
import org.junit.Before
import org.junit.Rule
import org.junit.Test

val newsArticles =
    listOf(
        NewsItemUiState(
            source = Source(id = "", name = "Biztoc.com"),
            author = "coingape.com",
            title = "Ethereum Founder Vitalik Buterin Reacts As Elon Musk Slams Microsoft",
            description = "In a remarkable and unforeseen turn of events witnessed recently, Ethereum founder Vitalik Buterin invited Elon Musk, the founder of X Corp. and Tesla, to team up...",
            url = "https://biztoc.com/x/89324d8b669c2591",
            urlToImage = "https://c.biztoc.com/p/89324d8b669c2591/s.webp",
            publishedAt = "2024-02-27T09:34:24Z",
            content = "In a remarkable and unforeseen turn of events witnessed recently, Ethereum founder Vitalik Buterin invited Elon Musk, the founder of X Corp. and Tesla, to team up...",
        ),
        NewsItemUiState(
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

class NewScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var newsViewModel: NewsViewModel

    @Before
    fun setupMocks() {
        newsViewModel =
            NewsViewModel(
                initialState = NewsViewState(newsItems = newsArticles),
                newsRepository = FakeNewsRepository(),
            )
    }

    @Test
    fun testNewsArticleLazyColumn() {
        // Start the app
        composeTestRule.setContent {
            NewsScreen(onArticleClicked = {}, viewModel = newsViewModel)
        }

        composeTestRule.onNodeWithTag("newsArticleLazyColumn").assertExists()
    }
}
