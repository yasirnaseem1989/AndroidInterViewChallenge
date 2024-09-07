package com.yasir.compose.androidinterviewchallenge.data.remote.interceptors

import com.yasir.compose.androidinterviewchallenge.persistence.sharedpreference.UserKeyValueStore
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class TokenInterceptor : Interceptor, KoinComponent {

    private val userKeyValueStore: UserKeyValueStore by inject()

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request = chain.request()
        synchronized(this) {
            val accessToken = userKeyValueStore.accessToken

            if (!accessToken.isNullOrEmpty()) {
                request = request
                    .newBuilder()
                    .header(HEADER_AUTHORIZATION, "$PREFIX_BEARER $accessToken")
                    .build()
            }
        }

        return chain.proceed(request)
    }

    companion object {
        private const val HEADER_AUTHORIZATION = "Authorization"
        private const val PREFIX_BEARER = "Bearer"
    }
}
