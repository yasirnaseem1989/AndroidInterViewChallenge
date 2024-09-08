package com.yasir.compose.androidinterviewchallenge.data.provider

import com.yasir.compose.androidinterviewchallenge.data.remote.model.LoginRequest
import com.yasir.compose.androidinterviewchallenge.data.remote.model.RemoteLogin
import com.yasir.compose.androidinterviewchallenge.domain.model.Login
import kotlin.random.Random


class FakeUserProvider {

    private val dummyUsers = mutableListOf(
        DummyUser(
            id = 1,
            email = "user1@example.com",
            password = "123",
            accessToken = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789"
        ),
        DummyUser(
            id = 1, email = "user2@example.com", password = "1234",
            accessToken = "ABCDEFGHIJKLMNOPQRSTUVWXYZasdsdfsdfghijklmnopqrstuvwxyz0123456789"
        ),
        DummyUser(
            id = 1, email = "user3@example.com", password = "12345",
            accessToken = "ABCDEFGHIJKLMNOPQRSTUVWererebfWE88fghijklmnopqrstuvwxyz0123456789"
        ),
        DummyUser(
            id = 1, email = "user4@example.com", password = "123456",
            accessToken = "ABCDEFGHIJKLMNOP23gf4VWXYZasdsdfsdfghijklmnopqrstuvwxyz0123456789"
        ),
        DummyUser(
            id = 1, email = "user5@example.com", password = "1234567",
            accessToken = "434gtBCDEFGHIJKLMNOPQRSTUVWXYZasdsdfsdfghijklmnopqrstuvwxyz0123456789"
        )
    )

    fun login(request: LoginRequest): DummyUser? =
        dummyUsers.find { it.email == request.email && it.password == request.password }


    data class DummyUser(
        val id: Int? = null,
        val email: String? = null,
        val password: String? = null,
        val accessToken: String? = null,
        val avatarUrl: String? = null
    )

    fun getUser(accessToken: String): DummyUser? =
        dummyUsers.find { it.accessToken == accessToken }
}