package com.example.valorantpickercompose.ui.theme

import androidx.compose.material3.Typography

val AppTypography = Typography().run {
    copy(
        displayLarge = displayLarge.copy(fontFamily = Tektur),
        displayMedium = displayMedium.copy(fontFamily = Tektur),
        displaySmall = displaySmall.copy(fontFamily = Tektur),

        headlineLarge = headlineLarge.copy(fontFamily = Tektur),
        headlineMedium = headlineMedium.copy(fontFamily = Tektur),
        headlineSmall = headlineSmall.copy(fontFamily = Tektur),

        titleLarge = titleLarge.copy(fontFamily = Tektur),
        titleMedium = titleMedium.copy(fontFamily = Tektur),
        titleSmall = titleSmall.copy(fontFamily = Tektur),

        bodyLarge = bodyLarge.copy(fontFamily = Tektur),
        bodyMedium = bodyMedium.copy(fontFamily = Tektur),
        bodySmall = bodySmall.copy(fontFamily = Tektur),

        labelLarge = labelLarge.copy(fontFamily = Tektur),
        labelMedium = labelMedium.copy(fontFamily = Tektur),
        labelSmall = labelSmall.copy(fontFamily = Tektur),
    )
}

