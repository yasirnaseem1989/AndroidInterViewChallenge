package com.yasir.compose.androidinterviewchallenge.data.mapper

import com.yasir.compose.androidinterviewchallenge.data.remote.model.RemoteLogin
import com.yasir.compose.androidinterviewchallenge.domain.model.Login

class LoginMapper {

    fun mapToDomain(remoteLogin: RemoteLogin): Login {
        return Login(
            userId = remoteLogin.userId,
            token = remoteLogin.token
        )
    }
}