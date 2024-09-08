package com.yasir.compose.androidinterviewchallenge.domain

import android.net.Uri
import com.yasir.compose.androidinterviewchallenge.data.provider.ImageHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class CompressImageUseCase(
    private val imageHelper: ImageHelper
) {
    suspend fun invoke(imageUri: Uri): File? {
        return withContext(Dispatchers.Default) {
            imageHelper.compressImage(imageUri)
        }
    }
}

