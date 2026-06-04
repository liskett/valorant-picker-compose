package com.example.valorantpickercompose.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.valorantpickercompose.R
import com.example.valorantpickercompose.presentation.viewmodel.AuthState

@Composable
fun SignInScreen(
    onSignUpClick: () -> Unit,
    onBackClick: () -> Unit,
    moveToChooseMapScreen: () -> Unit,
    onSignInClick: () -> Unit,
    loginState: AuthState,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
) {
    LaunchedEffect(loginState) {
        if (loginState is AuthState.Success) {
            moveToChooseMapScreen()
        }
    }

    val bg = MaterialTheme.colorScheme.background
    val accent = MaterialTheme.colorScheme.primary
    val errorColor = MaterialTheme.colorScheme.error

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = bg
    ) {
        Box(
            modifier = Modifier
                .drawWithCache {
                    val center = Offset(
                        x = size.width * 0.8f,
                        y = size.height * 0.2f
                    )
                    onDrawBehind {
                        drawRect(
                            brush = Brush.radialGradient(
                                colors = listOf(accent.copy(alpha = 0.5f), Color.Transparent),
                                center = center,
                                radius = size.minDimension * 0.9f
                            )
                        )
                    }
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_logo),
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = Color.Unspecified
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.sign_in_title),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(32.dp))

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = stringResource(R.string.username_label),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        modifier = Modifier.padding(start = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = email,
                        onValueChange = onEmailChange,
                        textStyle = TextStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 14.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp)),
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = stringResource(R.string.email_hint),
                                fontSize = 14.sp
                            )
                        },
                        isError = loginState is AuthState.Error,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            cursorColor = accent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = errorColor
                        )
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = stringResource(R.string.password_label),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        modifier = Modifier.padding(start = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        value = password,
                        onValueChange = onPasswordChange,
                        visualTransformation = PasswordVisualTransformation(),
                        textStyle = TextStyle(
                            color = MaterialTheme.colorScheme.onSurface,
                            fontSize = 14.sp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp)),
                        singleLine = true,
                        placeholder = {
                            Text(
                                text = stringResource(R.string.password_hint),
                                fontSize = 14.sp
                            )
                        },
                        isError = loginState is AuthState.Error,
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceContainer,
                            cursorColor = accent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = errorColor
                        )
                    )

                    // Показываем ошибку под полем пароля
                    if (loginState is AuthState.Error) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = stringResource(loginState.messageRes),
                            color = errorColor,
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = onSignInClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = accent,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = stringResource(R.string.sign_in_button),
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 18.sp,
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = onSignUpClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = accent
                    )
                ) {
                    Text(
                        text = stringResource(R.string.create_account_button),
                        fontSize = 14.sp,
                    )
                }
            }

            Icon(
                painter = painterResource(R.drawable.lucide_arrow_big_left),
                contentDescription = stringResource(R.string.back_content_description),
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp, top = 16.dp)
                    .clickable(onClick = onBackClick),
            )
        }
    }
}