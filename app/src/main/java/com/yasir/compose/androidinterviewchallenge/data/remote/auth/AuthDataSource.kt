package com.yasir.compose.androidinterviewchallenge.data.remote.auth

import com.yasir.compose.androidinterviewchallenge.data.remote.model.LoginRequest
import com.yasir.compose.androidinterviewchallenge.domain.model.Login
import com.yasir.compose.androidinterviewchallenge.data.repository.Result

interface AuthDataSource {

    suspend fun login(request: LoginRequest): Result<Login>
}