package com.hienle.cleanarchitecture.core.data.repository

import arrow.core.Either
import arrow.retrofit.adapter.either.networkhandling.CallError
import com.hienle.cleanarchitecture.core.model.ArticleResponse
import com.hienle.cleanarchitecture.core.network.CaNetworkDatasource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OfflineFirstNewsRepository(
    private val networkDatasource: CaNetworkDatasource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : NewsRepository {
    override suspend fun getTopHeadlines(country: String): Either<CallError, ArticleResponse> =
        withContext(ioDispatcher) {
            networkDatasource.getTopHeadlines(country)
        }
}