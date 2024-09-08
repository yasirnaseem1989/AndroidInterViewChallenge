package com.yasir.compose.androidinterviewchallenge.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.yasir.compose.androidinterviewchallenge.R
import com.yasir.compose.androidinterviewchallenge.R.string
import com.yasir.compose.androidinterviewchallenge.data.mapper.UriToFileMapper
import com.yasir.compose.androidinterviewchallenge.data.provider.ImageHelper
import com.yasir.compose.androidinterviewchallenge.ui.components.AppCard
import com.yasir.compose.androidinterviewchallenge.ui.components.AppInputField
import com.yasir.compose.androidinterviewchallenge.ui.components.AppProgressBar
import com.yasir.compose.androidinterviewchallenge.ui.components.AppText
import com.yasir.compose.androidinterviewchallenge.ui.components.ProfileImageComponent
import com.yasir.compose.androidinterviewchallenge.ui.theme.descriptionTextColor
import com.yasir.compose.androidinterviewchallenge.ui.theme.typoSemiBold
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import java.io.File

@Composable
fun ScreenHome(
    viewModel: HomeViewModel = koinViewModel(),
    uriToFileMapper: UriToFileMapper = koinInject(),
    imageHelper: ImageHelper = koinInject(),
) {
    var file by rememberSaveable {
        mutableStateOf<File?>(null)
    }
    var capturedImageUri by rememberSaveable {
        mutableStateOf<String?>(null)
    }

    val homeUiState by viewModel.homeUiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (homeUiState.isLoading) {
            AppProgressBar(
                modifier = Modifier.align(Alignment.Center)
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            if (homeUiState.hasData()) {
                AppCard(
                    modifier = Modifier.fillMaxSize(),
                    padding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    ProfileImageComponent(
                        placeHolderId = R.drawable.ic_user_rounded,
                        image = capturedImageUri ?: homeUiState.userInfo.avatarUrl,
                        imageHelper = imageHelper,
                        onImageCaptured = { uri ->
                            uri?.let {
                                file = uriToFileMapper.map(it)
                                file?.let { validFile ->
                                    capturedImageUri = validFile.toURI().toString()
                                    viewModel.handleImageCapture(validFile) { compressedImage ->
                                        viewModel.updateProfile(compressedImage)
                                    }
                                }
                            }
                        }
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    NameComponent(
                        titleId = R.string.email,
                        name = homeUiState.userInfo.email,
                        isEnable = false
                    ) {}

                    NameComponent(
                        titleId = R.string.password,
                        name = homeUiState.userInfo.password,
                        isEnable = false
                    ) {}
                }
            }
        }
    }
}

@Composable
private fun NameComponent(
    titleId: Int = R.string.name_text,
    name: String,
    isEnable: Boolean = true,
    onNameChange: (String) -> Unit
) {
    var isError by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        AppText(
            text = stringResource(id = titleId),
            color = descriptionTextColor,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )

        AppInputField(
            modifier = Modifier
                .fillMaxWidth(),
            text = name,
            enabled = isEnable,
            textStyle = typoSemiBold,
            supportingText = {
                if (isError) {
                    AppText(
                        text = stringResource(string.email_error),
                        fontWeight = FontWeight.Normal,
                        fontSize = 12.sp,
                        color = Color.Red,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }
            },
            placeholder = {
                AppText(
                    text = stringResource(id = string.enter_txt),
                )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email
            ),
            borderColor = Color(0xFFE6E8F7)
        ) {
            onNameChange.invoke(it.trimEnd())
            isError = it.isEmpty()
        }
    }
}