package com.yasir.compose.androidinterviewchallenge.data.provider

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class ImageHelper(
    private val context: Context
) {
    fun compressImage(imageUri: Uri): File? {
        return try {
            val inputStream = context.contentResolver.openInputStream(imageUri) ?: return null
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

            val fileOutputStream = FileOutputStream(compressedFile)
            originalBitmap.compress(Bitmap.CompressFormat.JPEG, quality, fileOutputStream)

            return compressedFile
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun handleImageRotation(uri: Uri): Bitmap? {
        return try {
            val inputStream: InputStream? = context.contentResolver.openInputStream(uri)
            val exif = ExifInterface(inputStream!!)
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)

            val bitmap = BitmapFactory.decodeStream(context.contentResolver.openInputStream(uri))
            val matrix = Matrix()

            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> matrix.postRotate(90f)
                ExifInterface.ORIENTATION_ROTATE_180 -> matrix.postRotate(180f)
                ExifInterface.ORIENTATION_ROTATE_270 -> matrix.postRotate(270f)
            }

            Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
