package com.hienle.cleanarchitecture

import android.app.Application
import com.hienle.cleanarchitecture.core.network.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            // Use application context whenever Context is injected
            androidContext(applicationContext)
            androidLogger()
            modules(networkModule)
        }
    }
}