package com.yasir.compose.androidinterviewchallenge.ui.components

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.yasir.compose.androidinterviewchallenge.R.drawable
import com.yasir.compose.androidinterviewchallenge.data.provider.ImageHelper
import com.yasir.compose.androidinterviewchallenge.ui.theme.AppFont
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun AppProgressBar(
    modifier: Modifier = Modifier,
    showProgress: Boolean = true
) {
    if (showProgress.not())
        return
    CircularProgressIndicator(
        modifier = modifier
    )
}

@Composable
fun AppButton(
    modifier: Modifier = Modifier,
    text: String,
    prefix: (@Composable () -> Unit)? = null,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    isEnabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        enabled = isEnabled,
        onClick = {
            onClick.invoke()
        },
        modifier = modifier
            .height(50.dp),
        colors = colors
    ) {
        prefix?.let {
            prefix()
        }
        Text(
            text = text,
            fontFamily = AppFont,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp
        )
    }
}

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    minHeight: Dp = 80.dp,
    contentColor: Color = Color.White,
    padding: PaddingValues = PaddingValues(0.dp),
    verticalArrangement: Arrangement.Vertical = Arrangement.Top,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .defaultMinSize(minHeight = minHeight)
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(contentColor)
            .padding(padding),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ) {
        content()
    }
}

@Composable
fun ProfileImageComponent(
    placeHolderId: Int = drawable.ic_user_rounded,
    image: String,
    onImageCaptured: (Uri?) -> Unit,
    imageHelper: ImageHelper,
) {
    var imageUri by rememberSaveable { mutableStateOf<Uri?>(null) }
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    var showDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val cameraPermissionGranted = remember { mutableStateOf(false) }

    val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val imageFile = remember { File(context.cacheDir, "camera_image_$timeStamp.jpg") }

    val imageFileUri: Uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.fileprovider",
        imageFile
    )

    val updatedOnImageCaptured by rememberUpdatedState(onImageCaptured)

    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        try {
            uri?.let {
                imageUri = it
                updatedOnImageCaptured(it)
                bitmap = imageHelper.handleImageRotation(it)
            }
        } catch (e: Exception) {
            Log.e("ProfileImageComponent", "Error handling image from gallery: ${e.message}")
        }
    }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture()
    ) { success: Boolean ->
        if (success) {
            bitmap = imageHelper.handleImageRotation(imageFileUri)
            imageUri = imageFileUri
            updatedOnImageCaptured(imageFileUri)
        }
    }

    val requestCameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        cameraPermissionGranted.value = isGranted
        if (!isGranted) {
            Log.e("ProfileImageComponent", "Camera permission denied")
        }
    }

    LaunchedEffect(Unit) {
        val permissionStatus = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.CAMERA
        )
        cameraPermissionGranted.value = permissionStatus == PackageManager.PERMISSION_GRANTED
    }

    DisposableEffect(Unit) {
        onDispose {
            imageFile.delete()
        }
    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = "Choose Image") },
            text = { Text(text = "Select from Gallery or Capture from Camera") },
            confirmButton = {
                AppButton(text = "Gallery",
                    onClick = {
                        showDialog = false
                        galleryLauncher.launch("image/*")
                    })
            },
            dismissButton = {
                AppButton(text = "Camera",
                    onClick = {
                        if (cameraPermissionGranted.value) {
                            cameraLauncher.launch(imageFileUri)
                        } else {
                            requestCameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        }
                        showDialog = false
                    })
            }
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        bitmap?.let {
            Image(
                bitmap = it.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .clip(CircleShape)
                    .size(80.dp)
                    .clickable { showDialog = true },
                contentScale = ContentScale.Crop
            )
        } ?: run {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(image)
                    .crossfade(true)
                    .diskCachePolicy(CachePolicy.ENABLED)
                    .build(),
                contentScale = ContentScale.Crop,
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .clickable { showDialog = true },
                loading = {
                    CircularProgressIndicator()
                },
                error = {
                    Image(
                        painter = painterResource(placeHolderId),
                        contentDescription = "",
                        modifier = Modifier.size(80.dp)
                    )
                }
            )
        }
    }
}



