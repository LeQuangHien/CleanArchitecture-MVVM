package com.hienle.cleanarchitecture.feature.news.article

import com.hienle.cleanarchitecture.core.common.architecture.ViewStateModel
import com.hienle.cleanarchitecture.core.data.repository.NewsRepository
import com.hienle.cleanarchitecture.core.model.Article
import com.hienle.cleanarchitecture.core.model.ArticleResponse
import com.hienle.cleanarchitecture.core.model.Source
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

data class NewsViewState(
    val showLoading: Boolean = true,
    val showError: Boolean = false,
    val newsItems: List<NewsItemUiState> = listOf(),
)

data class NewsItemUiState(
    val source: Source?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
)

class NewsViewModel(
    private val newsRepository: NewsRepository,
    initialState: NewsViewState = NewsViewState(),
    coroutineContext: CoroutineContext = Dispatchers.IO,
) : ViewStateModel<NewsViewState>(initialState, coroutineContext) {

    fun fetchTopHeadlines() {
        launch {
            getTopHeadlines()
        }
    }

    private suspend fun getTopHeadlines() {
        newsRepository.getTopHeadlines(country = "us").fold(
            ifLeft = {
                sendErrorState()
            },
            ifRight = {
                handleArticlesResult(it)
            },
        )
    }

    private fun mapNewsItemUiState(article: Article): NewsItemUiState {
        return NewsItemUiState(
            source = article.source,
            author = article.author,
            title = article.title,
            description = article.description,
            url = article.url,
            urlToImage = article.urlToImage,
            publishedAt = article.publishedAt,
            content = article.content,
        )
    }

    private fun handleArticlesResult(articleResponse: ArticleResponse) {
        val items: List<NewsItemUiState> =
            articleResponse.articles.map { article ->
                mapNewsItemUiState(article)
            }
        update {
            it.copy(
                newsItems = items,
                showLoading = false,
                showError = false,
            )
        }
    }

    private fun sendErrorState() {
        update {
            it.copy(
                showError = true,
                showLoading = false,
            )
        }
    }
}
