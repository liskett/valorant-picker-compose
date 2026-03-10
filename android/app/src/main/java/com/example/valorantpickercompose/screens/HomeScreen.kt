package com.example.valorantpickercompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.valorantpickercompose.R

@Composable
fun HomeScreen(
    onSignInClick: () -> Unit
) {
    val bgColor = MaterialTheme.colorScheme.background
    val accent = MaterialTheme.colorScheme.primary

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = bgColor
    ) {
        Box(
            modifier = Modifier
                .background(bgColor)
                .drawWithCache {
                    val center = Offset(
                        x = size.width * 0.75f,
                        y = size.height * 0.25f
                    )
                    onDrawBehind {
                        drawRect(
                            brush = Brush.radialGradient(
                                colors = listOf(accent.copy(alpha = 0.45f), Color.Transparent),
                                center = center,
                                radius = size.minDimension * 0.8f
                            )
                        )
                    }
                }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_logo),
                    contentDescription = null,
                    modifier = Modifier.size(96.dp),
                    tint = Color.Unspecified
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "VALORANT",
                    fontSize = 32.sp,
                    fontFamily = FontFamily(Font(R.font.valorantfont)),
                    color = MaterialTheme.colorScheme.onBackground,
                    letterSpacing = 4.sp
                )

                Text(
                    text = "PICKER",
                    fontSize = 32.sp,
                    fontFamily = FontFamily(Font(R.font.valorantfont)),
                    color = accent,
                    letterSpacing = 4.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(0.4f),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "Подбери идеального агента под карту и пик команды.",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(40.dp))

                PrimaryHomeButton(
                    text = "SIGN IN",
                    onClick = onSignInClick,
                    iconId = R.drawable.lucide_play
                )

                Spacer(modifier = Modifier.height(24.dp))

                SecondaryHomeButton(
                    text = "SETTINGS",
                    onClick = { },
                    iconId = R.drawable.lucide_settings
                )

                Spacer(modifier = Modifier.height(12.dp))

                SecondaryHomeButton(
                    text = "FAQ",
                    onClick = { },
                    iconId = R.drawable.lucide_circle_question_mark
                )

                Spacer(modifier = Modifier.height(12.dp))

                SecondaryHomeButton(
                    text = "CONTACTS",
                    onClick = { },
                    iconId = R.drawable.lucide_contact
                )
            }
        }
    }
}

@Composable
private fun PrimaryHomeButton(
    text: String,
    onClick: () -> Unit,
    iconId: Int
) {
    val accent = MaterialTheme.colorScheme.primary

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = accent,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Icon(
            painter = painterResource(iconId),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = text,
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(R.font.valorantfont))
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

@Composable
private fun SecondaryHomeButton(
    text: String,
    onClick: () -> Unit,
    iconId: Int
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.onBackground
        )
    ) {
        Icon(
            painter = painterResource(iconId),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = text,
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.valorantfont))
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
