package com.example.valorantpickercompose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.valorantpickercompose.viewmodel.PickerViewModel
import com.example.valorantpickercompose.R
import com.example.valorantpickercompose.ScreenRoutes

private val maps = listOf(
    "CORRODE","SUNSET","LOTUS","PEARL","FRACTURE",
    "BREEZE","ICEBOX","ASCENT","SPLIT","HAVEN","BIND","ABYSS"
)
val mapIcons = mapOf(
    "CORRODE" to R.drawable.corrode_map_icon,
    "SUNSET" to R.drawable.sunset_map_icon,
    "LOTUS" to R.drawable.lotus_map_icon,
    "PEARL" to R.drawable.pearl_map_icon,
    "FRACTURE" to R.drawable.fracture_map_icon,
    "BREEZE" to R.drawable.breeze_map_icon,
    "ICEBOX" to R.drawable.icebox_map_icon,
    "ASCENT" to R.drawable.ascent_map_icon,
    "SPLIT" to R.drawable.split_map_icon,
    "HAVEN" to R.drawable.haven_map_icon,
    "BIND" to R.drawable.bind_map_icon,
    "ABYSS" to R.drawable.abyss_map_icon
)

@Composable
fun ChooseMapScreen(navController: NavController, pickerViewModel: PickerViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(top=20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "CHOOSE MAP",
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.valorantfont))
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(maps) { mapName ->
                    MapCard(
                        mapName = mapName,
                        onClick = {
                            pickerViewModel.selectMap(mapName)
                            navController.navigate(ScreenRoutes.Agent.route)
                        }
                    )
                }
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

@Composable
fun MapCard(
    mapName: String,
    onClick: () -> Unit
) {
    val imageRes = mapIcons[mapName]
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick() }
    ) {
        Box(modifier = Modifier.fillMaxSize()) {

            if (imageRes != null) {
                Image(
                    painter = painterResource(imageRes),
                    contentDescription = mapName,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(120.dp),
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.DarkGray)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )

            Text(
                text = mapName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily(Font(R.font.valorantfont)),
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}
