package com.yasir.compose.androidinterviewchallenge.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.yasir.compose.androidinterviewchallenge.R

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
)

val AppFont = FontFamily(
    Font(R.font.poppinsmedium, FontWeight.Medium),
    Font(R.font.poppinsregular, FontWeight.Normal),
    Font(R.font.poppinsthin, FontWeight.Thin),
    Font(R.font.poppinssemibold, FontWeight.SemiBold),
)

val typoSemiBold = TextStyle(
    fontFamily = AppFont,
    fontWeight = FontWeight.SemiBold,
    fontSize = 16.sp,
)