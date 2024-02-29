package com.hienle.cleanarchitecture.feature.news.di

import com.hienle.cleanarchitecture.feature.news.article.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newsModule =
    module {
        viewModel { NewsViewModel(newsRepository = get()) }
    }
