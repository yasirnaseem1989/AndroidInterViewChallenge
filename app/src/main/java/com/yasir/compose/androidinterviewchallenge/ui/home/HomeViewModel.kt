package com.yasir.compose.androidinterviewchallenge.ui.home

import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yasir.compose.androidinterviewchallenge.data.provider.ImageHelper
import com.yasir.compose.androidinterviewchallenge.data.repository.ErrorType
import com.yasir.compose.androidinterviewchallenge.data.repository.ErrorType.Unknown
import com.yasir.compose.androidinterviewchallenge.data.repository.Result
import com.yasir.compose.androidinterviewchallenge.domain.CompressImageUseCase
import com.yasir.compose.androidinterviewchallenge.domain.GetUserInfoUseCase
import com.yasir.compose.androidinterviewchallenge.domain.UpdateProfileUseCase
import com.yasir.compose.androidinterviewchallenge.domain.model.UserInfo
import com.yasir.compose.androidinterviewchallenge.persistence.sharedpreference.UserKeyValueStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.File

class HomeViewModel(
    private val getUserInfoUseCase: GetUserInfoUseCase,
    private val compressImageUseCase: CompressImageUseCase,
    private val updateProfileUseCase: UpdateProfileUseCase,
    private val userKeyValueStore: UserKeyValueStore,
    private val imageHelper: ImageHelper,
) : ViewModel() {

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState = _homeUiState.asStateFlow()

    init {
        _homeUiState.update { homeUiState ->
            homeUiState.copy(
                isLoading = true,
            )
        }
        getUserInfo()
    }

    private fun getUserInfo() {
        viewModelScope.launch {
            val accessToken = userKeyValueStore.accessToken.orEmpty()
            when (val userInfoResult = getUserInfoUseCase.invoke(accessToken)) {
                is Result.Success -> {
                    _homeUiState.update { homeUiState ->
                        homeUiState.copy(
                            isLoading = false,
                            userInfo = userInfoResult.data
                        )
                    }
                }

                is Result.Error -> {
                    _homeUiState.update { homeUiState ->
                        homeUiState.copy(
                            isLoading = false,
                            isError = true,
                            errorType = userInfoResult.exception
                        )
                    }
                }
            }
        }
    }

    fun updateProfile(file: File?) {
        viewModelScope.launch {
            val base64Image = file?.let { imageHelper.encodeImageToBase64(it) }
            val userId = _homeUiState.value.userInfo.id
            updateProfileUseCase.invoke(userId, base64Image.orEmpty())
        }
    }

    fun handleImageCapture(file: File, onImageCompressed: (File?) -> Unit) {
        viewModelScope.launch {
            val compressedImageData = compressImageUseCase.invoke(file.toUri())
            onImageCompressed(compressedImageData)
        }
    }
}

data class HomeUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val isSuccess: Boolean = false,
    val userInfo: UserInfo = UserInfo(),
    val errorType: ErrorType = Unknown,
) {
    fun hasData(): Boolean = userInfo.email.isNotEmpty()
}