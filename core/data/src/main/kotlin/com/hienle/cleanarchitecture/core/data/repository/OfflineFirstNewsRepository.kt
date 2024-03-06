package com.hienle.cleanarchitecture.core.data.repository

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.hienle.cleanarchitecture.core.model.ArticleResponse
import com.hienle.cleanarchitecture.core.network.CaNetworkDatasource

class OfflineFirstNewsRepository(
    private val networkDatasource: CaNetworkDatasource,
) : NewsRepository {
    override suspend fun getTopHeadlines(country: String): Either<CallError, ArticleResponse> =
        networkDatasource.getTopHeadlines(country)
}
