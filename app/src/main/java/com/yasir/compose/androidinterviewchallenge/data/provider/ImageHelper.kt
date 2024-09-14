package com.yasir.compose.androidinterviewchallenge.data.provider

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import android.net.Uri
import android.util.Base64
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ImageHelper(
    private val context: Context
) {
    fun compressImage(imageUri: Uri): File? {
        return try {
            context.contentResolver.openInputStream(imageUri)?.use { inputStream ->
                val originalBitmap = BitmapFactory.decodeStream(inputStream)

                val compressedFile = File(context.cacheDir, "compressed_image.jpg")

                var quality = 100
                var streamLength: Int
                do {
                    val byteArrayOutputStream = ByteArrayOutputStream()
                    originalBitmap.compress(Bitmap.CompressFormat.JPEG, quality, byteArrayOutputStream)
                    val byteArray = byteArrayOutputStream.toByteArray()
                    streamLength = byteArray.size
                    quality -= 5
                } while (streamLength > 1_000_000 && quality > 50)

                FileOutputStream(compressedFile).use { fileOutputStream ->
                    originalBitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream)
                }

                return compressedFile
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    fun handleImageRotation(uri: Uri): Bitmap? {
        return try {
            context.contentResolver.openInputStream(uri)?.use { inputStream ->
                val exif = ExifInterface(inputStream)
                val orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )

                val bitmap =
                    BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
                val matrix = Matrix()

                when (orientation) {
                    ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(KEY_ROTATE_90)
                    ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(KEY_ROTATE_180)
                    ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(KEY_ROTATE_270)
                }

                Bitmap.createBitmap(
                    bitmap,
                    DEFAULT_HEIGHT_WIDTH_VALUE,
                    DEFAULT_HEIGHT_WIDTH_VALUE,
                    bitmap.width,
                    bitmap.height,
                    matrix, true
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    fun encodeImageToBase64(file: File): String {
        val bytes = FileInputStream(file).use { it.readBytes() }
        return Base64.encodeToString(bytes, Base64.NO_WRAP)
    }

    private companion object {
        const val DEFAULT_HEIGHT_WIDTH_VALUE = 0
        const val KEY_ROTATE_90 = 90f
        const val KEY_ROTATE_180 = 180f
        const val KEY_ROTATE_270 = 270f
    }
}
