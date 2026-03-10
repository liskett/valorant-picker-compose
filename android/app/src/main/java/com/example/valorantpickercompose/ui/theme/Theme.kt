package com.example.valorantpickercompose.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = ValorantRed,
    onPrimary = ValorantTextPrimary,

    background = ValorantBg,
    onBackground = ValorantTextPrimary,

    surface = ValorantSurface,
    onSurface = ValorantTextPrimary,

    surfaceVariant = ValorantSurfaceHigh,
    onSurfaceVariant = ValorantTextSecondary,

    outline = ValorantOutline,
)

private val DarkColorScheme = darkColorScheme(
    primary = ValorantRed,
    onPrimary = ValorantTextPrimary,

    background = ValorantBg,
    onBackground = ValorantTextPrimary,

    surface = ValorantSurface,
    onSurface = ValorantTextPrimary,

    surfaceVariant = ValorantSurfaceHigh,
    onSurfaceVariant = ValorantTextSecondary,

    outline = ValorantOutline,
)

@Composable
fun AppTheme(
    darkTheme: Boolean = true, // можно принудительно темную, чтобы всегда был Valorant‑вайб
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme || isSystemInDarkTheme()) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography,
        content = content
    )
}
