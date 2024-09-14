package com.yasir.compose.androidinterviewchallenge.data.provider

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import com.yasir.compose.androidinterviewchallenge.BuildConfig
import java.nio.charset.Charset

class EncryptionHelper {

    private val ANDROID_KEY_STORE = "AndroidKeyStore"
    private val AES_MODE = "AES/GCM/NoPadding"
    private val IV_SIZE = 12
    private val TAG_SIZE = 128
    private val KEY_SIZE = 256

    private val alias: String = BuildConfig.KEY_ALIAS

    private fun getSecretKey(): SecretKey {
        val keyStore = KeyStore.getInstance(ANDROID_KEY_STORE).apply {
            load(null)
        }

        return keyStore.getKey(alias, null) as? SecretKey ?: run {
            val keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, ANDROID_KEY_STORE)
            val keyGenParameterSpec = KeyGenParameterSpec.Builder(
                alias,
                KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
            )
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setKeySize(KEY_SIZE)
                .build()

            keyGenerator.init(keyGenParameterSpec)
            keyGenerator.generateKey()
        }
    }

    fun encrypt(plainText: String): String {
        val cipher = Cipher.getInstance(AES_MODE)
        cipher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        val iv = cipher.iv
        val cipherText = cipher.doFinal(plainText.toByteArray(Charset.forName("UTF-8")))

        val ivAndCipherText = ByteArray(IV_SIZE + cipherText.size).apply {
            System.arraycopy(iv, 0, this, 0, IV_SIZE)
            System.arraycopy(cipherText, 0, this, IV_SIZE, cipherText.size)
        }

        return Base64.encodeToString(ivAndCipherText, Base64.DEFAULT)
    }

    fun decrypt(encryptedText: String): String {
        val ivAndCipherText = Base64.decode(encryptedText, Base64.DEFAULT)
        val iv = ivAndCipherText.copyOfRange(0, IV_SIZE)
        val cipherText = ivAndCipherText.copyOfRange(IV_SIZE, ivAndCipherText.size)

        val cipher = Cipher.getInstance(AES_MODE)
        val spec = GCMParameterSpec(TAG_SIZE, iv)
        cipher.init(Cipher.DECRYPT_MODE, getSecretKey(), spec)

        val plainText = cipher.doFinal(cipherText)
        return String(plainText, Charset.forName("UTF-8"))
    }
}