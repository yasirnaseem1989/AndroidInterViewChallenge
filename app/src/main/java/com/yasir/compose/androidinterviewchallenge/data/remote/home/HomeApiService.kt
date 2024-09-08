package com.yasir.compose.androidinterviewchallenge.data.remote.home

import com.yasir.compose.androidinterviewchallenge.data.remote.model.RemoteAvatarResponse
import com.yasir.compose.androidinterviewchallenge.data.remote.model.RemoteUser
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HomeApiService {

    @GET("/users/{userid}")
    suspend fun getUser(
        @Path("userid") userId: Int,
        accessToken: String): RemoteUser

    @POST("/users/{userid}/avatar")
    suspend fun updateAvatar(
        @Path("userid") userId: Int,
        @Body avatar: Map<String, String>
    ): RemoteAvatarResponse
}