package com.yasir.compose.androidinterviewchallenge.domain

import com.yasir.compose.androidinterviewchallenge.data.remote.model.LoginRequest
import com.yasir.compose.androidinterviewchallenge.data.repository.AuthRepository
import com.yasir.compose.androidinterviewchallenge.domain.model.Login
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.yasir.compose.androidinterviewchallenge.data.repository.Result

class LoginUseCase(
    private val authRepository: AuthRepository,
) {
    suspend fun invoke(request: LoginRequest): Result<Login> =
        withContext(Dispatchers.IO) {
            authRepository.login(request)
        }
}

