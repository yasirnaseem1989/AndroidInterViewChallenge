package com.yasir.compose.androidinterviewchallenge.data.remote.home

import com.yasir.compose.androidinterviewchallenge.data.mapper.UserInfoMapper
import com.yasir.compose.androidinterviewchallenge.data.provider.FakeUserProvider
import com.yasir.compose.androidinterviewchallenge.data.remote.NetworkHandler
import com.yasir.compose.androidinterviewchallenge.data.remote.model.RemoteAvatarResponse
import com.yasir.compose.androidinterviewchallenge.data.repository.Result
import com.yasir.compose.androidinterviewchallenge.domain.model.UserInfo

class RemoteHomeDataSource (
    private val apiService: HomeApiService,
    private val networkHandler: NetworkHandler,
    private val userInfoMapper: UserInfoMapper,
    private val fakeUserProvider: FakeUserProvider,
) : HomeDataSource {

    override suspend fun getUser(accessToken: String): Result<UserInfo> {
        return networkHandler.safeApiCall {
            /*val remoteUserResponse =
                apiService.getUser(accessToken)*/ // When BE is ready use this function execute network call

            val remoteUser = fakeUserProvider.getUser(accessToken)

            if (remoteUser != null) {
                userInfoMapper.mapToDomain(remoteUser)
            } else {
                UserInfo()
            }
        }
    }

    override suspend fun updateAvatar(userId: Int, avatar: String): Result<RemoteAvatarResponse> {
        return networkHandler.safeApiCall {
            val requestBody = mapOf("avatar" to avatar)
            apiService.updateAvatar(userId, requestBody)
        }
    }
}