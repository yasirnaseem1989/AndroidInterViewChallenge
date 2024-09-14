package com.yasir.compose.androidinterviewchallenge.ui.login

import com.yasir.compose.androidinterviewchallenge.data.remote.model.LoginRequest
import com.yasir.compose.androidinterviewchallenge.data.repository.ErrorType
import com.yasir.compose.androidinterviewchallenge.data.repository.Result
import com.yasir.compose.androidinterviewchallenge.domain.LoginUseCase
import com.yasir.compose.androidinterviewchallenge.domain.model.Login
import com.yasir.compose.androidinterviewchallenge.persistence.sharedpreference.UserKeyValueStore
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScheduler
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class LoginViewModelTest {

    private lateinit var loginViewModel: LoginViewModel
    private val loginUseCase: LoginUseCase = mockk()
    private val userKeyValueStore: UserKeyValueStore = mockk(relaxed = true)
    private val testDispatcher = StandardTestDispatcher(TestCoroutineScheduler())

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        loginViewModel = LoginViewModel(loginUseCase, userKeyValueStore)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state should be correct`() = runBlocking {
        // Arrange
        val initialState = loginViewModel.loginUiState.first()

        // Assert
        assertFalse(initialState.isLoading)
        assertFalse(initialState.isError)
        assertFalse(initialState.isSuccess)
        assertEquals("", initialState.login.token)
        assertEquals(ErrorType.Unknown, initialState.errorType)
    }

    @Test
    fun `login success should update ui state and store access token`() = runBlocking {
        // Arrange
        val loginRequest = LoginRequest(email = "user1@example.com", password = "123")
        val expectedLogin = Login(userId = 1, token = "ABC123")
        coEvery { loginUseCase.invoke(loginRequest) } returns Result.Success(expectedLogin)

        // Act
        loginViewModel.login("user1@example.com", "123")

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val uiState = loginViewModel.loginUiState.first()
        assertFalse(uiState.isLoading)
        assertTrue(uiState.hasData())
        assertEquals(expectedLogin, uiState.login)

        // Check access token is stored after successful login
        coVerify { userKeyValueStore.accessToken = "ABC123" }
    }

    @Test
    fun `login failure should update ui state with error`() = runBlocking {
        // Arrange
        val loginRequest = LoginRequest(email = "user1@example.com", password = "123")
        val expectedError = Result.Error(ErrorType.Network)
        coEvery { loginUseCase.invoke(loginRequest) } returns expectedError

        // Act
        loginViewModel.login("user1@example.com", "123")

        testDispatcher.scheduler.advanceUntilIdle()

        // Assert
        val uiState = loginViewModel.loginUiState.first()
        assertFalse(uiState.isLoading)
        assertTrue(uiState.isError)
        assertEquals(ErrorType.Network, uiState.errorType)
    }
}
