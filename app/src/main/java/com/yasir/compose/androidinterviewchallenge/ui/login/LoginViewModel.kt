package com.yasir.compose.androidinterviewchallenge.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasir.compose.androidinterviewchallenge.data.remote.model.LoginRequest
import com.yasir.compose.androidinterviewchallenge.data.repository.ErrorType
import com.yasir.compose.androidinterviewchallenge.data.repository.ErrorType.Unknown
import com.yasir.compose.androidinterviewchallenge.domain.LoginUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.yasir.compose.androidinterviewchallenge.data.repository.Result
import com.yasir.compose.androidinterviewchallenge.domain.model.Login
import com.yasir.compose.androidinterviewchallenge.persistence.sharedpreference.UserKeyValueStore

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val userKeyValueStore: UserKeyValueStore,
) : ViewModel() {

    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState = _loginUiState.asStateFlow()

    fun login(
        email: String,
        password: String,
    ) {
        _loginUiState.update { loginUiState ->
            loginUiState.copy(
                isLoading = true,
            )
        }
        viewModelScope.launch {
            when (val loginResult =
                loginUseCase.invoke(LoginRequest(
                    email = email, password = password))) {
                is Result.Success -> {
                    userKeyValueStore.accessToken = loginResult.data.token
                    _loginUiState.update { loginUiState ->
                        loginUiState.copy(
                            isLoading = false,
                            login = loginResult.data
                        )
                    }
                }

                is Result.Error -> {
                    _loginUiState.update { loginUiState ->
                        loginUiState.copy(
                            isLoading = false,
                            isError = true,
                            errorType = loginResult.exception
                        )
                    }
                }
            }
        }
    }
}

data class LoginUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val login: Login = Login(),
    val errorType: ErrorType = Unknown,
) {
    fun hasData(): Boolean = login.token.isNotEmpty()
}