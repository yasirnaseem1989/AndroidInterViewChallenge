package com.yasir.compose.androidinterviewchallenge.persistence.sharedpreference

import android.content.Context
import com.google.gson.Gson

open class SharedPrefKeyValueStore(
    context: Context,
    key: String,
) : KeyValueStore {

    private val sp = context.getSharedPreferences(key, Context.MODE_PRIVATE)

    override fun contains(key: String) = sp.contains(key)

    override fun setString(key: String, value: String?) = sp.edit().putString(key, value).apply()

    override fun getString(key: String, defValue: String?): String? = sp.getString(key, defValue)

    override fun setBoolean(key: String, value: Boolean) = sp.edit().putBoolean(key, value).apply()

    override fun getBoolean(key: String, defValue: Boolean) = sp.getBoolean(key, defValue)

    override fun setInt(key: String, value: Int) = sp.edit().putInt(key, value).apply()

    override fun getInt(key: String, defValue: Int) = sp.getInt(key, defValue)

    override fun clear(key: String) {
        sp.edit().remove(key).apply()
    }

    override fun clear() {
        sp.edit().clear().apply()
    }

    override fun clearWithExceptions(vararg exceptions: String) {
        sp.all.map { it.key }
            .filterNot { exceptions.contains(it) }
            .forEach { clear(it) }
    }
}
