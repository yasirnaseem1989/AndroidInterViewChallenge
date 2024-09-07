package com.yasir.compose.androidinterviewchallenge.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yasir.compose.androidinterviewchallenge.R
import com.yasir.compose.androidinterviewchallenge.R.drawable
import com.yasir.compose.androidinterviewchallenge.R.string
import com.yasir.compose.androidinterviewchallenge.ui.theme.AppFont
import com.yasir.compose.androidinterviewchallenge.ui.theme.typoSemiBold

@Composable
fun AppText(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    text: String,
    fontWeight: FontWeight = FontWeight.Normal,
    fontSize: TextUnit = 16.sp,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit? = null,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    letterSpacing: TextUnit = TextUnit.Unspecified,
) {
    Text(
        text = text,
        fontFamily = AppFont,
        fontWeight = fontWeight,
        fontSize = fontSize,
        color = color,
        modifier = modifier,
        textAlign = textAlign,
        lineHeight = lineHeight ?: fontSize,
        maxLines = maxLines,
        overflow = overflow,
        letterSpacing = letterSpacing
    )
}

@Composable
fun WelcomeTitle(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.primary,
    text: String,
    fontSize: TextUnit = 24.sp
) {
    AppText(
        text = text,
        fontWeight = FontWeight.Medium,
        fontSize = fontSize,
        color = color,
        modifier = modifier,
        lineHeight = fontSize,
    )
}

@Composable
fun EmailComponent(
    modifier: Modifier = Modifier,
    email: String,
    isEmailError: Boolean = false,
    borderColor: Color = Color.White,
    onEmail: (String) -> Unit
) {

    val context = LocalContext.current

    AppInputField(
        modifier = modifier
            .fillMaxWidth(),
        text = email,
        textStyle = typoSemiBold,
        leadingIcon = {
            Image(
                imageVector = ImageVector.vectorResource(R.drawable.ic_mail),
                contentDescription = "emailIcon"
            )
        },
        supportingText = {
            if (isEmailError) {
                AppText(
                    text = stringResource(id = R.string.email_error),
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }
        },
        placeholder = {
            AppText(
                text = context.getString(R.string.email),
            )
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next
        ),
        borderColor = borderColor
    ) {
        onEmail.invoke(it)
    }
}

@Composable
fun AppInputField(
    modifier: Modifier = Modifier,
    text: String,
    supportingText: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    label: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: @Composable (() -> Unit)? = null,
    textStyle: TextStyle = LocalTextStyle.current,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    borderColor: Color = Color.White,
    enabled: Boolean = true,
    disabledTextColor: Color = Color.Unspecified,
    singleLine: Boolean = false,
    onValueChange: ((String) -> Unit)? = null,
) {
    OutlinedTextField(
        value = text,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = borderColor,
            unfocusedBorderColor = borderColor,
            unfocusedContainerColor = Color.White,
            focusedContainerColor = Color.White,
            disabledTextColor = disabledTextColor
        ),
        label = label,
        shape = RoundedCornerShape(10.dp),
        supportingText = supportingText,
        leadingIcon = leadingIcon,
        placeholder = placeholder,
        modifier = modifier,
        onValueChange = {
            onValueChange?.invoke(it)
        },
        keyboardOptions = keyboardOptions,
        trailingIcon = trailingIcon,
        textStyle = textStyle,
        visualTransformation = visualTransformation,
        enabled = enabled,
        singleLine = singleLine
    )
}

@Composable
fun PasswordComponent(
    password: String,
    modifier: Modifier = Modifier,
    placeHolder: String? = null,
    borderColor: Color = Color.White,
    showPasswordPolicy: Boolean = false,
    onPasswordInfoClick: (() -> Unit)? = null,
    showLeadingIcon: Boolean = true,
    onPassword: (String) -> Unit
) {
    val context = LocalContext.current

    var isShowPassword by remember {
        mutableStateOf(false)
    }
    var isError by remember {
        mutableStateOf(false)
    }

    AppInputField(
        modifier = modifier
            .defaultMinSize(minHeight = 40.dp)
            .fillMaxWidth(),
        text = password,
        trailingIcon = {
            Image(
                imageVector = if (isShowPassword)
                    ImageVector.vectorResource(drawable.ic_eye)
                else
                    ImageVector.vectorResource(drawable.ic_eye_closed),
                contentDescription = "show password",
                modifier = Modifier.clickable {
                    isShowPassword = !isShowPassword
                }
            )
        },
        supportingText = {
            if (isError) {
                AppText(
                    text = context.getString(R.string.password_error),
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Color.Red,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            } else if (showPasswordPolicy)
                TextButton(onClick = {
                    onPasswordInfoClick?.invoke()
                }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        AppText(
                            text = context.getString(string.password_strength),
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(bottom = 4.dp, top = 4.dp)
                        )
                        Icon(
                            painter = painterResource(id = drawable.ic_info),
                            contentDescription = ""
                        )
                    }
                }
        },
        textStyle = typoSemiBold,
        leadingIcon = {
            if (showLeadingIcon)
                Image(
                    imageVector = ImageVector.vectorResource(drawable.ic_password),
                    contentDescription = "password",
                )
        },
        placeholder = {
            AppText(
                text = placeHolder ?: stringResource(string.password),
            )
        },
        visualTransformation = if (isShowPassword) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        borderColor = borderColor
    ) {
        isError = it.isEmpty()
        onPassword.invoke(it)
    }
}


