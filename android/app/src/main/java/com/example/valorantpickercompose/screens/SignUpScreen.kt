package com.example.valorantpickercompose.screens

import android.widget.EditText
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SecureTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.valorantpickercompose.R
import com.example.valorantpickercompose.ScreenRoutes
import com.example.valorantpickercompose.data.model.AuthResponse
import com.example.valorantpickercompose.viewmodel.AuthState
import com.example.valorantpickercompose.viewmodel.AuthViewModel
import java.nio.file.Files.size

@Composable
fun SignUpScreen(navController: NavController, authViewModel: AuthViewModel) {
    val registerState by authViewModel.registerState.collectAsState()
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    if (registerState is AuthState.Success) {
        LaunchedEffect(Unit) {
            navController.navigate(ScreenRoutes.SignIn.route)
        }

    }
    Box(
        modifier = Modifier
            .drawWithCache {
                val center = Offset(
                    x = size.width / 2f,
                    y = size.height * 0.40f
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
            modifier = Modifier
                .fillMaxSize()

                .padding(top = 60.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_logo),
                contentDescription = null,
                modifier = Modifier.size(100.dp))

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text="SIGN UP",
                fontSize = 36.sp,
                fontFamily = FontFamily(Font(R.font.valorantfont)),
                textAlign = TextAlign.Center,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text="USERNAME",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.valorantfont)),
                    color = Color.White,
                    modifier = Modifier.padding(start = 20.dp)
                )
                Spacer(modifier = Modifier. height(12.dp))
                TextField(
                    value = email,
                    onValueChange = {email=it},
                    textStyle = TextStyle(
                        color=Color(0x52FFFFFF),
                        fontFamily = FontFamily(Font(R.font.valorantfont))),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp,end=20.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(3.dp, Color.White, RoundedCornerShape(8.dp)),
                    colors = TextFieldDefaults.colors(unfocusedContainerColor = Color(0xFF541313))
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text="PASSWORD",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.valorantfont)),
                    color = Color.White,
                    modifier = Modifier.padding(start = 20.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                TextField(
                    value = password,
                    onValueChange = { password = it },
                    visualTransformation = PasswordVisualTransformation(),
                    textStyle = TextStyle(
                        color = Color(0x52FFFFFF),
                        fontFamily = FontFamily(Font(R.font.valorantfont))
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .border(3.dp, Color.White, RoundedCornerShape(8.dp)),
                    colors = TextFieldDefaults.colors(unfocusedContainerColor = Color(0xFF541313))
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { authViewModel.register(email, password) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                    .border(3.dp,Color.Black,RoundedCornerShape(30.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFE42B2B))
            ) {
                Text(
                    text="SIGN UP",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.valorantfont)),
                    color = Color.White,
                )
            }
        }
        Icon(
            painter = painterResource(R.drawable.lucide_arrow_big_left),
            contentDescription = "back",
            tint = Color.White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 20.dp, top = 30.dp)
                .clickable{navController.navigateUp()},
        )
    }
}
