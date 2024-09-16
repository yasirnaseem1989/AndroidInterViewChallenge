package com.yasir.compose.androidinterviewchallenge.ui

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.assertIsDisplayed
import androidx.navigation.testing.TestNavHostController
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.ComposeNavigator
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.yasir.compose.androidinterviewchallenge.AssessmentApp
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AssessmentAppTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController

    @Before
    fun setUpNavController() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current)
            navController.navigatorProvider.addNavigator(ComposeNavigator())

            AssessmentApp(
                navController = navController,
                startDestination = "login")
        }
    }

    @Test
    fun verifyLoginScreenIsDisplayed() {
        waitForSplashScreenToDisappear()
        assertScreenIsDisplayed("Login")
    }

    @Test
    fun verifyHomeScreenAfterLoginSuccess() {
        waitForSplashScreenToDisappear()
        simulateNavigationTo("home")
        assertScreenIsDisplayed("Home")
    }

    private fun assertScreenIsDisplayed(contentDescription: String) {
        composeTestRule
            .onNodeWithContentDescription(contentDescription)
            .assertIsDisplayed()
    }

    private fun simulateNavigationTo(destination: String) {
        composeTestRule.runOnUiThread {
            navController.navigate(destination)
        }
    }

    private fun waitForSplashScreenToDisappear() {
        composeTestRule.waitForIdle()
    }
}
