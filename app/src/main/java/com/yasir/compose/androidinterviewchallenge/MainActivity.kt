package com.yasir.compose.androidinterviewchallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.yasir.compose.androidinterviewchallenge.ui.login.ScreenLogin
import com.yasir.compose.androidinterviewchallenge.ui.theme.AndroidInterViewChallengeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidInterViewChallengeTheme {
               AssessmentApp()
            }
        }
    }
}

@Composable
fun AssessmentApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "login") {
        composable("login") {
            ScreenLogin(
                navController = navController
            )
        }
    }
}