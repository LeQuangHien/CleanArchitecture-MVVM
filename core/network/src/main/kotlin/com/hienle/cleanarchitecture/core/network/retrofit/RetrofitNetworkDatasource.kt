package com.hienle.cleanarchitecture.core.network.retrofit

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.hienle.cleanarchitecture.core.model.ArticleResponse
import com.hienle.cleanarchitecture.core.network.CaNetworkDatasource
import com.hienle.cleanarchitecture.core.network.service.NewsService

class RetrofitNetworkDatasource(
    private val newsService: NewsService
) : CaNetworkDatasource {
    override suspend fun getTopHeadlines(country: String): Either<CallError, ArticleResponse> =
        newsService.getTopHeadlines(country)
}