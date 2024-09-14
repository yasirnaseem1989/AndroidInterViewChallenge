package com.yasir.compose.androidinterviewchallenge.persistence.sharedpreference

import android.content.Context
import com.yasir.compose.androidinterviewchallenge.data.provider.EncryptionHelper
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

open class SharedPrefKeyValueStore(
    context: Context,
    key: String,
) : KeyValueStore, KoinComponent {

    private val sp = context.getSharedPreferences(key, Context.MODE_PRIVATE)
    private val encryptionHelper: EncryptionHelper by inject()

    override fun contains(key: String) = sp.contains(key)

    override fun setString(key: String, value: String?) {
        val encryptedValue = value?.let { encryptionHelper.encrypt(it) }
        sp.edit().putString(key, encryptedValue).apply()
    }

    override fun getString(key: String, defValue: String?): String? {
        val encryptedValue = sp.getString(key, null) ?: return defValue
        return encryptionHelper.decrypt(encryptedValue)
    }

    override fun setBoolean(key: String, value: Boolean) {
        val encryptedValue = encryptionHelper.encrypt(value.toString())
        sp.edit().putString(key, encryptedValue).apply()
    }

    override fun getBoolean(key: String, defValue: Boolean): Boolean {
        val encryptedValue = sp.getString(key, null) ?: return defValue
        return encryptionHelper.decrypt(encryptedValue).toBoolean()
    }

    override fun setInt(key: String, value: Int) {
        val encryptedValue = encryptionHelper.encrypt(value.toString())
        sp.edit().putString(key, encryptedValue).apply()
    }

    override fun getInt(key: String, defValue: Int): Int {
        val encryptedValue = sp.getString(key, null) ?: return defValue
        return encryptionHelper.decrypt(encryptedValue).toInt()
    }

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

