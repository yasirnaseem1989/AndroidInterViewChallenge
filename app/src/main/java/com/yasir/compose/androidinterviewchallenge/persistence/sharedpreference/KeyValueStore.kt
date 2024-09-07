package com.yasir.compose.androidinterviewchallenge.persistence.sharedpreference

interface KeyValueStore {

    operator fun contains(key: String): Boolean

    fun setString(key: String, value: String?)
    fun getString(key: String, defValue: String?): String?

    fun setBoolean(key: String, value: Boolean)
    fun getBoolean(key: String, defValue: Boolean): Boolean

    fun setInt(key: String, value: Int)
    fun getInt(key: String, defValue: Int): Int

    fun clear(key: String)
    fun clear()
    fun clearWithExceptions(vararg exceptions: String)
}
