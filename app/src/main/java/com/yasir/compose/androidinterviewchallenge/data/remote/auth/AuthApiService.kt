package com.yasir.compose.androidinterviewchallenge.data.remote.auth

import com.yasir.compose.androidinterviewchallenge.data.remote.model.LoginRequest
import com.yasir.compose.androidinterviewchallenge.data.remote.model.RemoteLogin
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("sessions/new")
    suspend fun login(@Body request: LoginRequest): RemoteLogin
}