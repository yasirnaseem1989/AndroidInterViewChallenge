package com.yasir.compose.androidinterviewchallenge.utils

import android.content.Context
import android.os.Build
import android.provider.Settings
import android.util.Patterns


object Utils {

    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}