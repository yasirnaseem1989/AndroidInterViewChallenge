package com.yasir.compose.androidinterviewchallenge.data.remote.auth

import com.yasir.compose.androidinterviewchallenge.data.mapper.LoginMapper
import com.yasir.compose.androidinterviewchallenge.data.provider.FakeUserProvider
import com.yasir.compose.androidinterviewchallenge.data.remote.NetworkHandler
import com.yasir.compose.androidinterviewchallenge.data.remote.model.LoginRequest
import com.yasir.compose.androidinterviewchallenge.domain.model.Login
import com.yasir.compose.androidinterviewchallenge.data.repository.Result

class RemoteAuthDataSource (
    private val apiService: AuthApiService,
    private val networkHandler: NetworkHandler,
    private val loginMapper: LoginMapper,
    private val fakeUserProvider: FakeUserProvider,
) : AuthDataSource {


    override suspend fun login(request: LoginRequest): Result<Login> {
        return networkHandler.safeApiCall {
            /*val remoteLoginResponse =
                apiService.login(request)*/ // When BE is ready use this function execute network call

            val remoteUser = fakeUserProvider.login(request)

            if (remoteUser != null) {
                loginMapper.mapToDomain(remoteUser)
            } else {
                Login()
            }
        }
    }
}