package com.yasir.compose.androidinterviewchallenge.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.yasir.compose.androidinterviewchallenge.BuildConfig
import com.yasir.compose.androidinterviewchallenge.data.remote.NetworkHandler
import com.yasir.compose.androidinterviewchallenge.data.remote.auth.AuthApiService
import com.yasir.compose.androidinterviewchallenge.data.remote.home.HomeApiService
import com.yasir.compose.androidinterviewchallenge.data.remote.interceptors.TokenInterceptor
import okhttp3.CertificatePinner
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

internal const val OKHTTP_FOR_NETWORK = "OKHTTP_FOR_NETWORK"
internal const val INTERCEPTOR_TOKEN = "INTERCEPTOR_TOKEN"
internal const val INTERCEPTOR_LOGGING = "INTERCEPTOR_LOGGING"
internal const val CLIENT_RETROFIT = "CLIENT_RETROFIT"

const val TIMEOUT_CONNECT = 30L
const val TIMEOUT_READ = 30L
const val TIMEOUT_WRITE = 30L

val networkModule = module {

    single {
        NetworkHandler()
    }

    single<Gson> {
        GsonBuilder().create()
    }


    factory {
        CertificatePinner.Builder().build()
    }

    single<Interceptor>(named(INTERCEPTOR_TOKEN)) {
        TokenInterceptor()
    }

    single<Interceptor>(named(INTERCEPTOR_LOGGING)) {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        httpLoggingInterceptor
    }


    single(named(OKHTTP_FOR_NETWORK)) {
        OkHttpClient.Builder()
            .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
            .addInterceptor(get<Interceptor>(named(INTERCEPTOR_TOKEN)))
            .addInterceptor(get<Interceptor>(named(INTERCEPTOR_LOGGING)))
            .certificatePinner(get())
            .build()
    }

    single(named(CLIENT_RETROFIT)) {
        Retrofit.Builder()
            .baseUrl("https://your-api-base-url.com")
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get(named(OKHTTP_FOR_NETWORK)))
            .build()
    }

    single {
        get<Retrofit>(named(CLIENT_RETROFIT)).create(AuthApiService::class.java)
    }

    single {
        get<Retrofit>(named(CLIENT_RETROFIT)).create(HomeApiService::class.java)
    }
}
