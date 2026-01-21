package com.example.valorantpickercompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.navigation.NavController
import com.example.valorantpickercompose.R
import com.example.valorantpickercompose.ScreenRoutes

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .drawWithCache {
                val center = Offset(
                    x = size.width / 2f,
                    y = size.height * 0.30f
                )

                onDrawBehind {
                    drawRect(
                        brush = Brush.radialGradient(
                            colors = listOf(Color(0xFFE42B2B), Color(0xFF671717)),
                            center = center,
                            radius = size.minDimension / 2f
                        )
                    )
                }
            }
    ){
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = null,
                modifier = Modifier.size(220.dp))

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text="VALORANT\nPICKER",
                fontSize = 54.sp,
                fontFamily = FontFamily(Font(R.font.valorantfont)),
                textAlign = TextAlign.Center,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(50.dp))

            HomeScreenButton(
                text = "SIGN IN",
                onClick = { navController.navigate(ScreenRoutes.SignIn.route)},
                iconId = R.drawable.lucide_play
            )

            Spacer(modifier = Modifier.height(20.dp))

            HomeScreenButton(
                text = "SETTINGS",
                onClick = {},
                iconId = R.drawable.lucide_settings
            )

            Spacer(modifier = Modifier.height(20.dp))

            HomeScreenButton(
                text = "FAQ",
                onClick = {},
                iconId = R.drawable.lucide_circle_question_mark
            )

            Spacer(modifier = Modifier.height(20.dp))

            HomeScreenButton(
                text = "CONTACTS",
                onClick = {},
                iconId = R.drawable.lucide_contact
            )
        }
    }

}

@Composable
fun HomeScreenButton(
    text: String,
    onClick: () -> Unit,
    iconId: Int
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(start = 50.dp, end = 50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                painter = painterResource(iconId),
                contentDescription = null,
                modifier = Modifier.size(40.dp)
            )

            Text(
                text=text,
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.valorantfont)),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(30.dp))
        }
    }
}
