package com.hienle.cleanarchitecture.core.network.di

import arrow.retrofit.adapter.either.EitherCallAdapterFactory
import com.google.gson.GsonBuilder
import com.hienle.cleanarchitecture.core.network.service.NewsService
import com.hienle.cleanarchitecture.core.network.util.InstantDateDeserializer
import com.hienle.cleanarchitecture.core.network.util.LocalTimeDeserializer
import com.hienle.cleanarchitecture.core.network.util.LocalTimeSerializer
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.Instant
import java.time.LocalTime
import java.util.concurrent.TimeUnit

fun provideHttpClient(): OkHttpClient.Builder {
    val builder = OkHttpClient.Builder()
    builder.connectTimeout(30, TimeUnit.SECONDS)
    builder.readTimeout(30, TimeUnit.SECONDS)
    return builder
}

fun provideGsonConverterFactory(): GsonConverterFactory {
    val gson = GsonBuilder()
        .registerTypeAdapter(Instant::class.java, InstantDateDeserializer())
        .registerTypeAdapter(LocalTime::class.java, LocalTimeDeserializer())
        .registerTypeAdapter(LocalTime::class.java, LocalTimeSerializer())
        .create()
    return GsonConverterFactory.create(gson)
}

fun provideCommonInterceptor(): Interceptor {
    return Interceptor { chain ->
        val reqBuilder = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("Authorization", "7fbe9415fb614d65b75621da46646c6e")
        chain.proceed(reqBuilder.build())
    }
}

fun provideEitherCallAdapterFactory(): EitherCallAdapterFactory {
    return EitherCallAdapterFactory()
}

fun provideRetrofit(
    httpClientBuilder: OkHttpClient.Builder,
    gsonConverterFactory: GsonConverterFactory,
    commonInterceptor: Interceptor,
    eitherCallAdapterFactory: EitherCallAdapterFactory
): Retrofit {
    httpClientBuilder.addInterceptor(commonInterceptor)
    return Retrofit.Builder()
        .baseUrl("https://newsapi.org/")
        .client(httpClientBuilder.build())
        .addConverterFactory(gsonConverterFactory)
        .addCallAdapterFactory(eitherCallAdapterFactory)
        .build()
}

fun provideService(retrofit: Retrofit): NewsService =
    retrofit.create(NewsService::class.java)

val networkModule = module {
    single { provideHttpClient() }
    single { provideGsonConverterFactory() }
    single { provideCommonInterceptor() }
    single { provideEitherCallAdapterFactory() }
    single { provideRetrofit(get(), get(), get(), get()) }
    single { provideService(get()) }
}
