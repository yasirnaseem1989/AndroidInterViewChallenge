package com.yasir.compose.androidinterviewchallenge.data.mapper

import com.yasir.compose.androidinterviewchallenge.data.provider.FakeUserProvider.DummyUser
import com.yasir.compose.androidinterviewchallenge.domain.model.Login
import com.yasir.compose.androidinterviewchallenge.domain.model.UserInfo
import com.yasir.compose.androidinterviewchallenge.utils.ext.orZero

class UserInfoMapper {

    fun mapToDomain(remoteLogin: DummyUser): UserInfo {
        return UserInfo(
            id = remoteLogin.id.orZero(),
            email = remoteLogin.email.orEmpty(),
            password = remoteLogin.password.orEmpty(),
            avatarUrl = remoteLogin.accessToken.orEmpty(),
        )
    }
}