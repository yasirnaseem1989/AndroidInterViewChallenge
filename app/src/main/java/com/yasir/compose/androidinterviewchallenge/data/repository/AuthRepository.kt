package com.yasir.compose.androidinterviewchallenge.data.repository

import com.yasir.compose.androidinterviewchallenge.data.remote.auth.AuthDataSource
import com.yasir.compose.androidinterviewchallenge.data.remote.model.LoginRequest
import com.yasir.compose.androidinterviewchallenge.domain.model.Login

class AuthRepository(
    private val remoteAuthDataSource: AuthDataSource
) {
    suspend fun login(request: LoginRequest): Result<Login> =
        remoteAuthDataSource.login(request)
}