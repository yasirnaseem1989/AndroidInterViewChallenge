package com.yasir.compose.androidinterviewchallenge.data.mapper

import android.content.Context
import android.net.Uri
import java.io.File

class UriToFileMapper(private val context: Context) {

    fun map(uri: Uri): File? {
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        val file = File(context.cacheDir, "temp_file")
        inputStream.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }
        return file
    }
}

