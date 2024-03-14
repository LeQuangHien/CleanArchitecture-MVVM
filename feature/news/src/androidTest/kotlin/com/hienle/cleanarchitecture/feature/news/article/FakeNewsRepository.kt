package com.hienle.cleanarchitecture.feature.news.article

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.hienle.cleanarchitecture.core.data.repository.NewsRepository
import com.hienle.cleanarchitecture.core.model.ArticleResponse

class FakeNewsRepository() : NewsRepository {
    override suspend fun getTopHeadlines(country: String): Either<CallError, ArticleResponse> {
        return Either.Right(ArticleResponse(status = "", totalResults = 0, articles = listOf()))
    }
}
