package com.hienle.cleanarchitecture.core.data.repository

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.hienle.cleanarchitecture.core.model.ArticleResponse

interface NewsRepository {
    suspend fun getTopHeadlines(country: String): Either<CallError, ArticleResponse>
}