package com.example.valorantpickercompose.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.radialGradient(
                    listOf(Color(0xFFE42B2B), Color(0xFF671717))
                )
            ),
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
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(50.dp))

        HomeScreenButton(
            text = "START",
            onClick = { navController.navigate(ScreenRoutes.ChooseMap.route) }
        )

        Spacer(modifier = Modifier.height(20.dp))

        HomeScreenButton(
            text = "SETTINGS",
            onClick = {}
        )

        Spacer(modifier = Modifier.height(20.dp))

        HomeScreenButton(
            text = "FAQ",
            onClick = {}
        )

        Spacer(modifier = Modifier.height(20.dp))

        HomeScreenButton(
            text = "CONTACTS",
            onClick = {}
        )
    }
}

@Composable
fun HomeScreenButton(
    text: String,
    onClick: () -> Unit,
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
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_launcher_background),
                contentDescription = null,
                modifier = Modifier.size(40.dp).clip(RoundedCornerShape(50.dp))
            )
            Text(
                text=text,
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.valorantfont))
            )
        }
    }
}
