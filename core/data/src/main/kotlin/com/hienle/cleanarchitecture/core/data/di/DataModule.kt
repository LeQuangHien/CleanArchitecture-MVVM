package com.hienle.cleanarchitecture.core.data.di

import com.hienle.cleanarchitecture.core.data.repository.NewsRepository
import com.hienle.cleanarchitecture.core.data.repository.OfflineFirstNewsRepository
import org.koin.dsl.module

val dataModule = module {
    single<NewsRepository> { OfflineFirstNewsRepository(get()) }
}