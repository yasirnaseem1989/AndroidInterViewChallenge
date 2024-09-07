package com.yasir.compose.androidinterviewchallenge.persistence.sharedpreference

import android.content.Context
import com.google.gson.Gson

class UserKeyValueStore(context: Context) :
    SharedPrefKeyValueStore(context, USER_KEY_VALUE_STORE) {

    var accessToken: String?
        get() {
            return getString(ACCESS_TOKEN, null)
        }
        set(value) {
            setString(ACCESS_TOKEN, value)
        }

    companion object {
        private const val USER_KEY_VALUE_STORE = "user_key_value_store"
        private const val ACCESS_TOKEN = "access_token"
    }
}
