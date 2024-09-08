package com.yasir.compose.androidinterviewchallenge.data.remote.home

import com.yasir.compose.androidinterviewchallenge.data.remote.model.RemoteUser
import retrofit2.http.GET

interface HomeApiService {

    @GET("/users/{userid}")
    suspend fun getUser(accessToken: String): RemoteUser
}