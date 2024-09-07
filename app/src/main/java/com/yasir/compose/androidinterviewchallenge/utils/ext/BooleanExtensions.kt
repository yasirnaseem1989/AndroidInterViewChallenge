package com.yasir.compose.androidinterviewchallenge.utils.ext

fun Boolean?.orFalse(): Boolean = this ?: false
fun Boolean?.orTrue(): Boolean = this ?: true
