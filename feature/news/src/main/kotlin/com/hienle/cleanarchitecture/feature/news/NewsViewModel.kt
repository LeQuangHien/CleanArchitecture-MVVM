package com.hienle.cleanarchitecture.feature.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hienle.cleanarchitecture.core.data.repository.NewsRepository
import com.hienle.cleanarchitecture.core.model.Article
import com.hienle.cleanarchitecture.core.model.Source
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal data class NewsViewState(
    val showLoading: Boolean = true,
    val showError: Boolean = false,
    val newsItems: List<NewsItemUiState> = listOf(),
)

internal data class NewsItemUiState(
    val source: Source?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
)

internal class NewsViewModel(
    private val newsRepository: NewsRepository
) : ViewModel() {

    init {
        getTopHeadlines()
    }

    private var fetchJob: Job? = null

    private val _uiState = MutableStateFlow(NewsViewState(showLoading = true))
    val uiState: StateFlow<NewsViewState> = _uiState.asStateFlow()

    private fun getTopHeadlines() {
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            newsRepository.getTopHeadlines(country = "de").fold(
                ifLeft = {
                    _uiState.update {
                        it.copy(showError = true, showLoading = false)
                    }
                },
                ifRight = { articleResponse ->
                    _uiState.update {
                        val items: List<NewsItemUiState> =
                            articleResponse.articles.map { article ->
                                mapNewsItemUiState(article)
                            }
                        it.copy(newsItems = items, showLoading = false)
                    }
                }
            )
        }
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
            content = article.content
        )
    }
}