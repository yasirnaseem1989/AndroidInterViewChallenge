package com.yasir.compose.androidinterviewchallenge.domain

import com.yasir.compose.androidinterviewchallenge.data.remote.model.LoginRequest
import com.yasir.compose.androidinterviewchallenge.data.repository.AuthRepository
import com.yasir.compose.androidinterviewchallenge.data.repository.ErrorType
import com.yasir.compose.androidinterviewchallenge.domain.model.Login
import com.yasir.compose.androidinterviewchallenge.data.repository.Result
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LoginUseCaseTest {

    private lateinit var loginUseCase: LoginUseCase
    private val authRepository: AuthRepository = mockk()

    @Before
    fun setUp() {
        loginUseCase = LoginUseCase(authRepository)
    }

    @Test
    fun `login success should return success result`() = runBlocking {
        // Arrange
        val loginRequest = LoginRequest(email = "user1@example.com", password = "123")
        val expectedLogin = Login(userId = 1, token = "ABC123")
        coEvery { authRepository.login(loginRequest) } returns Result.Success(expectedLogin)

        // Act
        val result = withContext(Dispatchers.IO) { loginUseCase.invoke(loginRequest) }

        // Assert
        assertTrue(result is Result.Success)
        assertEquals(expectedLogin, (result as Result.Success).data)
    }

    @Test
    fun `login failure should return error result`() = runBlocking {
        // Arrange
        val loginRequest = LoginRequest(email = "user1@example.com", password = "123")
        val expectedError = Result.Error(ErrorType.Generic("Login failed"))
        coEvery { authRepository.login(loginRequest) } returns expectedError

        // Act
        val result = withContext(Dispatchers.IO) { loginUseCase.invoke(loginRequest) }

        // Assert
        assertTrue(result is Result.Error)
        assertEquals(ErrorType.Generic("Login failed"), (result as Result.Error).exception)
    }

    @Test(expected = Exception::class)
    fun `login should propagate exception on unexpected error`(): Unit = runBlocking {
        // Arrange
        val loginRequest = LoginRequest(email = "user1@example.com", password = "123")
        coEvery { authRepository.login(loginRequest) } throws Exception("Network error")

        // Act / Assert
        loginUseCase.invoke(loginRequest)
    }
}
