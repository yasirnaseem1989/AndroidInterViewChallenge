package com.yasir.compose.androidinterviewchallenge.data.remote.auth

import com.yasir.compose.androidinterviewchallenge.data.mapper.LoginMapper
import com.yasir.compose.androidinterviewchallenge.data.remote.NetworkHandler
import com.yasir.compose.androidinterviewchallenge.data.remote.model.LoginRequest
import com.yasir.compose.androidinterviewchallenge.domain.model.Login
import com.yasir.compose.androidinterviewchallenge.data.repository.Result

class RemoteAuthDataSource (
    private val apiService: AuthApiService,
    private val networkHandler: NetworkHandler,
    private val loginMapper: LoginMapper,
) : AuthDataSource {


    override suspend fun login(request: LoginRequest): Result<Login> {
        return networkHandler.safeApiCall {
            val remoteLoginResponse = apiService.login(request)
            if (remoteLoginResponse.userId.isEmpty()) {
                Login()
            } else {
                loginMapper.mapToDomain(remoteLoginResponse)
            }
        }
    }
}