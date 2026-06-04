package com.example.valorantpickercompose.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.valorantpickercompose.R
import com.example.valorantpickercompose.domain.model.Agent
import com.example.valorantpickercompose.presentation.mapper.AgentUiMapper
import com.example.valorantpickercompose.presentation.viewmodel.PickerState

@Composable
fun AgentScreen(
    pickerState: PickerState,
    onSelectAgent: (Agent) -> Unit,
    onRemoveAgent: (Agent) -> Unit,
    onToResultClick: () -> Unit,
    onBackClick: () -> Unit
) {
    val bg = MaterialTheme.colorScheme.background
    val accent = MaterialTheme.colorScheme.primary

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
                        text = stringResource(R.string.choose_agent_title),
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    repeat(4) { index ->
                        if (index < pickerState.selectedAgents.size) {
                            SelectedAgentIcon(
                                agent = pickerState.selectedAgents[index],
                                onRemove = { onRemoveAgent(pickerState.selectedAgents[index]) }
                            )
                        } else {
                            EmptySlot()
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(430.dp),
                    contentPadding = PaddingValues(12.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(Agent.entries) { agent ->
                        val isSelected = pickerState.selectedAgents.contains(agent)
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier
                                .clip(RoundedCornerShape(24.dp))
                                .background(
                                    if (isSelected)
                                        MaterialTheme.colorScheme.surfaceVariant
                                    else
                                        MaterialTheme.colorScheme.surface
                                )
                                .border(
                                    width = if (isSelected) 2.dp else 0.dp,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                                    shape = RoundedCornerShape(24.dp)
                                )
                        ) {
                            Image(
                                painter = painterResource(AgentUiMapper.getIcon(agent)),
                                contentDescription = agent.name,
                                modifier = Modifier
                                    .size(72.dp)
                                    .clickable { onSelectAgent(agent) }
                            )

                            if (isSelected) {
                                Box(
                                    modifier = Modifier
                                        .matchParentSize()
                                        .background(
                                            Brush.radialGradient(
                                                colors = listOf(
                                                    MaterialTheme.colorScheme.primary.copy(alpha = 0.18f),
                                                    Color.Transparent
                                                )
                                            )
                                        )
                                )
                            }
                        }
                    }

                }

                Spacer(modifier = Modifier.height(24.dp))

                if (pickerState.selectedAgents.size == 4) {
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = accent),
                        onClick = onToResultClick
                    ) {
                        Text(
                            stringResource(R.string.to_result_button),
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            Icon(
                painter = painterResource(R.drawable.lucide_arrow_big_left),
                contentDescription = "back",
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(start = 16.dp, top = 16.dp)
                    .clickable { onBackClick() },
            )
        }
    }
}

@Composable
fun SelectedAgentIcon(
    agent: Agent,
    onRemove: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(72.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline,
                shape = RoundedCornerShape(8.dp)
            ),
        contentAlignment = Alignment.BottomCenter,
    ) {
        Image(
            painter = painterResource(AgentUiMapper.getIcon(agent)),
            contentDescription = agent.name,
            modifier = Modifier
                .size(72.dp)
                .clickable { onRemove() }
        )
    }
}

@Composable
fun EmptySlot() {
    Box(
        modifier = Modifier
            .size(72.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                shape = RoundedCornerShape(8.dp)
            )
    )
}
