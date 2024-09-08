package com.yasir.compose.androidinterviewchallenge.data.repository

import com.yasir.compose.androidinterviewchallenge.data.remote.home.HomeDataSource
import com.yasir.compose.androidinterviewchallenge.domain.model.UserInfo

class HomeRepository(
    private val remoteHomeDataSource: HomeDataSource
) {
    suspend fun getUserInfo(accessToken: String): Result<UserInfo> =
        remoteHomeDataSource.getUser(accessToken)
}