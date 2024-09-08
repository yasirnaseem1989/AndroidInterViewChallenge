package com.yasir.compose.androidinterviewchallenge.data.repository

import com.yasir.compose.androidinterviewchallenge.data.remote.auth.AuthDataSource
import com.yasir.compose.androidinterviewchallenge.data.remote.model.LoginRequest
import com.yasir.compose.androidinterviewchallenge.domain.model.Login
import com.yasir.compose.androidinterviewchallenge.data.repository.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class AuthRepositoryTest {

    private lateinit var authRepository: AuthRepository
    private val remoteAuthDataSource: AuthDataSource = mockk()

    @Before
    fun setUp() {
        authRepository = AuthRepository(remoteAuthDataSource)
    }

    @Test
    fun `login success should return success result`() = runBlocking {
        // Arrange
        val loginRequest = LoginRequest(email = "user1@example.com", password = "123")
        val expectedLogin = Login(userId = 1, token = "ABC123")
        coEvery { remoteAuthDataSource.login(loginRequest) } returns Result.Success(expectedLogin)

        // Act
        val result = authRepository.login(loginRequest)

        // Assert
        assertTrue(result is Result.Success)
        assertEquals(expectedLogin, (result as Result.Success).data)
    }

    @Test
    fun `login failure should return error result`() = runBlocking {
        // Arrange
        val loginRequest = LoginRequest(email = "user1@example.com", password = "123")
        val expectedError = Result.Error(ErrorType.Generic("Login failed"))
        coEvery { remoteAuthDataSource.login(loginRequest) } returns expectedError

        // Act
        val result = authRepository.login(loginRequest)

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(ErrorType.Generic("Login failed"), (result as Result.Error).exception)
    }

    @Test(expected = Exception::class)
    fun `login should throw exception on unexpected error`(): Unit = runBlocking {
        // Arrange
        val loginRequest = LoginRequest(email = "user1@example.com", password = "123")
        coEvery { remoteAuthDataSource.login(loginRequest) } throws Exception("Network error")

        // Act / Assert
        authRepository.login(loginRequest)
    }
}
