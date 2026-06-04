package com.example.valorantpickercompose.presentation.screens

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.valorantpickercompose.R

@Composable
fun HomeScreen(
    onSignInClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onContactsClick: () -> Unit
) {
    val bgColor = MaterialTheme.colorScheme.background
    val primaryColor = MaterialTheme.colorScheme.primary

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = bgColor
    ) {
        Box(
            modifier = Modifier
                .background(bgColor)
                .drawWithCache { // позволяет рисовать что-то вручную с возможностью кэширования ресурсов
                    //точка на экране с координатами (x, y)
                    val center = Offset(
                        x = size.width * 0.75f,
                        y = size.height * 0.25f
                    )
                    onDrawBehind { // сама отрисовка позади всех дочерних элементов
                        drawRect( // рисует прямоугольник
                            //brush - то, чем заполнить этот прямоугольник
                            brush = Brush.radialGradient( //круг, в котором цвета меняются от центра к краям, т.е градиент
                                colors = listOf(primaryColor.copy(alpha = 0.45f), Color.Transparent),
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
                    contentDescription = "logo",
                    modifier = Modifier.size(96.dp),
                    tint = Color.Unspecified
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "VALORANT",
                    fontSize = 32.sp,
                    fontFamily = FontFamily(Font(R.font.valorantfont)),
                    color = MaterialTheme.colorScheme.onBackground,
                    letterSpacing = 4.sp // расстояние между буквами
                )

                Text(
                    text = "PICKER",
                    fontSize = 32.sp,
                    fontFamily = FontFamily(Font(R.font.valorantfont)),
                    color = primaryColor,
                    letterSpacing = 4.sp
                )

                Spacer(modifier = Modifier.height(24.dp))

                HorizontalDivider( // просто полоса разделитель
                    modifier = Modifier.fillMaxWidth(0.4f),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f) // берем тот же цвет, но делаем чуть более прозрачным
                )

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = stringResource(R.string.home_screen_subtitle),
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                    textAlign = TextAlign.Start
                )

                Spacer(modifier = Modifier.height(40.dp))

                PrimaryHomeButton(
                    onClick = onSignInClick,
                    iconId = R.drawable.lucide_play
                )

                Spacer(modifier = Modifier.height(24.dp))

                SecondaryHomeButton(
                    text = stringResource(R.string.settings_title),
                    onClick = onSettingsClick,
                    iconId = R.drawable.lucide_settings
                )

                Spacer(modifier = Modifier.height(12.dp))
                /*
                SecondaryHomeButton(
                    text = "FAQ",
                    onClick = { },
                    iconId = R.drawable.lucide_circle_question_mark
                )

                Spacer(modifier = Modifier.height(12.dp))
                */
                SecondaryHomeButton(
                    text = stringResource(R.string.contacts_title),
                    onClick = onContactsClick,
                    iconId = R.drawable.lucide_contact
                )
            }
        }
    }
}

//кнопка для перехода на SignInScreen, сделал красной чтобы выделялась как основная
@Composable
private fun PrimaryHomeButton(
    onClick: () -> Unit,
    iconId: Int
) {
    val primaryColor = MaterialTheme.colorScheme.primary

    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = primaryColor,
            contentColor = MaterialTheme.colorScheme.onPrimary
        )
    ) {
        Icon(
            painter = painterResource(iconId),
            contentDescription = "SignIn icon",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.sign_in_title),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}

// компоуз функция для других кнопок(второстепенных), здесь есть параметр text в конструкторе, тк их несколько на экране
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
            contentDescription = "secondary icon(settings/faq/contacts)",
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}
