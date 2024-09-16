package com.yasir.compose.androidinterviewchallenge.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.yasir.compose.androidinterviewchallenge.R
import com.yasir.compose.androidinterviewchallenge.ui.components.AppButton
import com.yasir.compose.androidinterviewchallenge.ui.components.AppProgressBar
import com.yasir.compose.androidinterviewchallenge.ui.components.AppText
import com.yasir.compose.androidinterviewchallenge.ui.components.EmailComponent
import com.yasir.compose.androidinterviewchallenge.ui.components.PasswordComponent
import com.yasir.compose.androidinterviewchallenge.ui.components.WelcomeTitle
import com.yasir.compose.androidinterviewchallenge.utils.Utils
import com.yasir.compose.androidinterviewchallenge.utils.ext.orZero
import org.koin.androidx.compose.koinViewModel

@Composable
fun ScreenLogin(
    viewModel: LoginViewModel = koinViewModel(),
    onLoginSuccess: (Int) -> Unit
) {
    val context = LocalContext.current
    var email by remember {
        mutableStateOf("")
    }
    var isEmailError by remember {
        mutableStateOf(false)
    }

    var password by remember {
        mutableStateOf("")
    }
    var isPasswordError by remember {
        mutableStateOf(false)
    }

    val loginUiState by viewModel.loginUiState.collectAsStateWithLifecycle()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .semantics { contentDescription = "Login" }
            .padding(bottom = 24.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            WelcomeTitle(
                text = context.getString(R.string.welcome_back),
                fontSize = 40.sp,
                modifier = Modifier.padding(top = 24.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppText(
                text = context.getString(R.string.login_message),
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 18.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            EmailComponent(
                modifier = Modifier.padding(top = 16.dp),
                email = email,
                isEmailError = isEmailError,
            ) {
                email = it.trimEnd()
            }

            PasswordComponent(
                password = password,
                modifier = Modifier.padding(top = 8.dp)
            ) {
                password = it
            }
        }

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AppButton(
                    text = context.getString(R.string.button_label_login),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                ) {
                    if (Utils.isValidEmail(email).not()) {
                        isEmailError = true
                        return@AppButton
                    }
                    isEmailError = false
                    if (password.isEmpty()) {
                        isPasswordError = true
                        return@AppButton
                    }
                    isPasswordError = false

                    viewModel.login(
                        email = email.trimEnd(),
                        password = password,
                    )
                }
            }

        if (loginUiState.isLoading) {
            AppProgressBar(
                modifier = Modifier.align(Alignment.Center)
            )
        }

        if (loginUiState.hasData()) onLoginSuccess.invoke(loginUiState.login.userId.orZero())
    }
}
