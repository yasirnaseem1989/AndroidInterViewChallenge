package com.yasir.compose.androidinterviewchallenge.domain

import com.yasir.compose.androidinterviewchallenge.data.repository.HomeRepository
import com.yasir.compose.androidinterviewchallenge.data.repository.Result
import com.yasir.compose.androidinterviewchallenge.domain.model.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetUserInfoUseCase(
    private val homeRepository: HomeRepository,
) {
    suspend fun invoke(accessToken: String): Result<UserInfo> =
        withContext(Dispatchers.IO) {
            homeRepository.getUserInfo(accessToken)
        }
}

