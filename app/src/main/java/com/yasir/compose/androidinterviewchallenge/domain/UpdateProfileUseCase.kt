package com.yasir.compose.androidinterviewchallenge.domain

import com.yasir.compose.androidinterviewchallenge.data.remote.model.RemoteAvatarResponse
import com.yasir.compose.androidinterviewchallenge.data.repository.HomeRepository
import com.yasir.compose.androidinterviewchallenge.data.repository.Result
import com.yasir.compose.androidinterviewchallenge.domain.model.UserInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UpdateProfileUseCase(
    private val homeRepository: HomeRepository,
) {
    suspend fun invoke(userId: Int, avatar: String): Result<RemoteAvatarResponse> =
        withContext(Dispatchers.IO) {
            homeRepository.updateProfile(userId, avatar)
        }
}

