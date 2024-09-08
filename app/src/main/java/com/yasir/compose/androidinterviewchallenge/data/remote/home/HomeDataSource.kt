package com.yasir.compose.androidinterviewchallenge.data.remote.home

import com.yasir.compose.androidinterviewchallenge.data.repository.Result
import com.yasir.compose.androidinterviewchallenge.domain.model.UserInfo

interface HomeDataSource {

    suspend fun getUser(accessToken: String): Result<UserInfo>
}