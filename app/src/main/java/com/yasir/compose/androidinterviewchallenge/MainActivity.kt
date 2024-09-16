package com.yasir.compose.androidinterviewchallenge

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.yasir.compose.androidinterviewchallenge.persistence.sharedpreference.UserKeyValueStore
import com.yasir.compose.androidinterviewchallenge.ui.home.ScreenHome
import com.yasir.compose.androidinterviewchallenge.ui.login.ScreenLogin
import com.yasir.compose.androidinterviewchallenge.ui.theme.AndroidInterViewChallengeTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val userKeyValueStore: UserKeyValueStore by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        installSplashScreen()


        val startDestination = if (userKeyValueStore.accessToken.isNullOrEmpty()) {
            "login"
        } else {
            "home"
        }
        setContent {
            AndroidInterViewChallengeTheme {
                val navController = rememberNavController()
                AssessmentApp(
                    navController = navController,
                    startDestination = startDestination
                )
            }
        }
    }
}

@Composable
fun AssessmentApp(navController: NavHostController, startDestination: String) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login") {
            ScreenLogin(
                onLoginSuccess = {
                    navController.popBackStack("login", true)
                    navController.navigate("home") {
                        launchSingleTop = true
                    }
                }
            )
        }
        composable("home") {
            ScreenHome()
        }
    }
}