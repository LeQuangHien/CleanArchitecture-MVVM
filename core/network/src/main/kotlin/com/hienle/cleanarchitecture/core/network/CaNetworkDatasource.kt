package com.hienle.cleanarchitecture.core.network

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.hienle.cleanarchitecture.core.model.ArticleResponse

interface CaNetworkDatasource {
    suspend fun getTopHeadlines(country: String): Either<CallError, ArticleResponse>
}