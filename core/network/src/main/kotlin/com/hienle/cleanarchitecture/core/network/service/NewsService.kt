package com.hienle.cleanarchitecture.core.network.service

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.hienle.cleanarchitecture.core.model.ArticleResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("/v2/top-headlines")
    suspend fun getTopHeadlines(@Query("country") country: String): Either<CallError, ArticleResponse>
}