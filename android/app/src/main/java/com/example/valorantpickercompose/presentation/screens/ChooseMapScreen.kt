package com.example.valorantpickercompose.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.valorantpickercompose.R
import com.example.valorantpickercompose.domain.model.GameMap
import com.example.valorantpickercompose.presentation.mapper.MapUiMapper


@Composable
fun ChooseMapScreen(
    selectedMap: GameMap?,
    onMapClick: (GameMap) -> Unit
) {
    val bg = MaterialTheme.colorScheme.background

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = bg
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(bg)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(80.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.choose_map_title),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(GameMap.entries, key = { it.name }) { mapName ->
                        MapCard(
                            mapName = mapName,
                            isSelected = mapName == selectedMap,
                            onClick = { onMapClick(mapName) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MapCard(
    mapName: GameMap,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val imageRes = MapUiMapper.getIcon(mapName)
    val borderColor = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(imageRes),
                contentDescription = stringResource(R.string.map_content_description, mapName.name),
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            // Glow
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.radialGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
                                    Color.Transparent
                                ),
                                center = Offset(0.5f, 0.5f),
                                radius = 300f
                            )
                        )
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.35f))
                )
            }

            Text(
                text = mapName.name,
                fontSize = 22.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}