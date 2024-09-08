package com.yasir.compose.androidinterviewchallenge

import android.app.Application
import com.yasir.compose.androidinterviewchallenge.di.authModule
import com.yasir.compose.androidinterviewchallenge.di.homeModule
import com.yasir.compose.androidinterviewchallenge.di.networkModule
import com.yasir.compose.androidinterviewchallenge.di.persistenceModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            val koinModules = listOf(
                networkModule,
                persistenceModule,
                authModule,
                homeModule,
            )
            modules(koinModules)
        }
    }
}