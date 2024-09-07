package com.yasir.compose.androidinterviewchallenge.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yasir.compose.androidinterviewchallenge.ui.theme.AppFont

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