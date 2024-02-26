package com.hienle.cleanarchitecture.core.model

data class ArticleResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)