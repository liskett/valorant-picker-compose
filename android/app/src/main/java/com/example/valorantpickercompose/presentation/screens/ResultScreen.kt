package com.example.valorantpickercompose.presentation.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.valorantpickercompose.R
import com.example.valorantpickercompose.domain.model.Agent
import com.example.valorantpickercompose.domain.model.AgentStats
import com.example.valorantpickercompose.domain.model.GameMap
import com.example.valorantpickercompose.domain.model.Recommendation
import com.example.valorantpickercompose.presentation.mapper.AgentUiMapper
import com.example.valorantpickercompose.presentation.mapper.MedalUiMapper
import com.example.valorantpickercompose.presentation.viewmodel.PickerViewModel
import com.example.valorantpickercompose.presentation.viewmodel.ResultUiState

@Composable
fun ResultScreen(
    onBackClick: () -> Unit,
    selectedMap: GameMap?,
    selectedAgents: List<Agent>,
    viewModel: PickerViewModel
) {
    val bg = MaterialTheme.colorScheme.background
    val accent = MaterialTheme.colorScheme.primary
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.loadResult(selectedMap, selectedAgents)
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = bg
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .drawWithCache {
                    val center = Offset(
                        x = size.width * 0.1f,
                        y = size.height * 0.8f
                    )
                    onDrawBehind {
                        drawRect(
                            brush = Brush.radialGradient(
                                colors = listOf(accent.copy(alpha = 0.4f), Color.Transparent),
                                center = center,
                                radius = size.minDimension * 0.8f
                            )
                        )
                    }
                }
        ) {
            when (uiState) {
                is ResultUiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = accent)
                    }
                }

                is ResultUiState.Success -> {
                    val state = uiState as ResultUiState.Success
                    ResultContent(
                        selectedMap = selectedMap,
                        selectedAgents = selectedAgents,
                        mapStats = state.mapStats,
                        recommendation = state.recommendation,
                        accent = accent,
                        onBackClick = onBackClick
                    )
                }

                is ResultUiState.Error -> {
                    val state = uiState as ResultUiState.Error
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = stringResource(R.string.error_loading_title),
                                fontSize = 18.sp,
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = state.message,
                                fontSize = 14.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = { viewModel.loadResult(selectedMap, selectedAgents) },
                                colors = ButtonDefaults.buttonColors(containerColor = accent)
                            ) {
                                Text(stringResource(R.string.retry_button))
                            }
                        }
                    }
                }

                else -> {
                    Box(modifier = Modifier.fillMaxSize())
                }
            }
        }
    }
}

@Composable
private fun ResultContent(
    selectedMap: GameMap?,
    selectedAgents: List<Agent>,
    mapStats: Map<Agent, AgentStats>,
    recommendation: Recommendation,
    accent: Color,
    onBackClick: () -> Unit
) {
    val topAgent = recommendation.topAgent?.name
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        item {
            Text(
                text = stringResource(R.string.recommendation_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                thickness = 1.dp,
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
            )

            Spacer(modifier = Modifier.height(24.dp))
        }




        item{
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    Image(
                        painterResource(AgentUiMapper.getArtwork(topAgent)),
                        contentDescription = null
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        text=topAgent?.name.toString(),
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }




        // Рекомендованные пики
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.recommended_picks_label),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )
                    if (recommendation.suggestedByRole.isEmpty()) {
                        Text(
                            text = stringResource(R.string.comp_already_covers_roles),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        recommendation.suggestedByRole.forEach { (role, agents) ->
                            Text(
                                text = stringResource(R.string.role_group_title, role.name),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.padding(top = 8.dp)
                            )

                            agents.forEachIndexed { index, agentInfo ->
                                val stats = mapStats[agentInfo.name]
                                val wrPercent = "%.1f".format((stats?.winRate ?: 0.0) * 100)
                                val prPercent = "%.1f".format((stats?.pickRate ?: 0.0) * 100)
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Image(
                                        painter=painterResource(MedalUiMapper.getMedal(index)),
                                        contentDescription = "medal",
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(Modifier.width(6.dp))
                                    Text(
                                        text = stringResource(R.string.agent_stats_line, agentInfo.name, wrPercent, prPercent),
                                        fontSize = 14.sp,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                }

                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
        // Карта
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.map_label),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )
                    Text(
                        text = selectedMap?.name?.let { stringResource(R.string.map_name, it) } ?: stringResource(R.string.unknown_map),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Выбранные агенты
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = stringResource(R.string.your_team_agents_label),
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                    )

                    if (selectedAgents.isEmpty()) {
                        Text(
                            text = stringResource(R.string.no_agents_selected),
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            selectedAgents.forEach { agent ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = agent.name,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )
                                    Icon(
                                        painter = painterResource(AgentUiMapper.getIcon(agent)),
                                        contentDescription = stringResource(R.string.agent_icon_description, agent.name),
                                        tint = Color.Unspecified,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
        item {
            Button(
                onClick = onBackClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = accent
                )
            ) {
                Text(
                    text = stringResource(R.string.back_button),
                    fontSize = 16.sp,
                )
            }
        }
    }
}