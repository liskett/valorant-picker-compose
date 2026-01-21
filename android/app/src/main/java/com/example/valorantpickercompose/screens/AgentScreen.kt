package com.example.valorantpickercompose.screens


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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.valorantpickercompose.ScreenRoutes
import com.example.valorantpickercompose.viewmodel.AgentViewModel
import com.example.valorantpickercompose.R



val agentIcons = mapOf(
    "Astra" to R.drawable.astra_icon,
    "Breach" to R.drawable.breach_icon,
    "Brimstone" to R.drawable.brimstone_icon,
    "Chamber" to R.drawable.chamber_icon,
    "Clove" to R.drawable.clove_icon,
    "Cypher" to R.drawable.cypher_icon,
    "Deadlock" to R.drawable.deadlock_icon,
    "Fade" to R.drawable.fade_icon,
    "Gekko" to R.drawable.gekko_icon,
    "Harbor" to R.drawable.harbor_icon,
    "Iso" to R.drawable.iso_icon,
    "Jett" to R.drawable.jett_icon,
    "Kayo" to R.drawable.kayo_icon,
    "Killjoy" to R.drawable.killjoy_icon,
    "Neon" to R.drawable.neon_icon,
    "Omen" to R.drawable.omen_icon,
    "Phoenix" to R.drawable.phoenix_icon,
    "Raze" to R.drawable.raze_icon,
    "Reyna" to R.drawable.reyna_icon,
    "Sage" to R.drawable.sage_icon,
    "Skye" to R.drawable.skye_icon,
    "Sova" to R.drawable.sova_icon,
    "Tejo" to R.drawable.tejo_icon,
    "Veto" to R.drawable.veto_icon,
    "Viper" to R.drawable.viper_icon,
    "Vyse" to R.drawable.vyse_icon,
    "Waylay" to R.drawable.waylay_icon,
    "Yoru" to R.drawable.yoru_icon,

)

@Composable
fun AgentScreen(navController: NavController, agentViewModel: AgentViewModel) {
    val state by agentViewModel.state.collectAsState()

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
                    text = "CHOOSE AGENTS",
                    color = Color.White,
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = FontFamily(Font(R.font.valorantfont))
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                repeat(4) { index ->
                    if (index < state.selectedAgents.size) {
                        SelectedAgentIcon(
                            agentName = state.selectedAgents[index],
                            onRemove = {
                                agentViewModel.removeAgent(state.selectedAgents[index])
                            }
                        )
                    } else {
                        EmptySlot()
                    }
                }
            }

            Spacer(modifier = Modifier.height(60.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.fillMaxWidth().height(430.dp),
                contentPadding = PaddingValues(12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(agentIcons.keys.toList()) { agentName ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .clip(RoundedCornerShape(30.dp))
                            .background(color=Color(0xFF671717))

                    ) {
                        Image(
                            painter = painterResource(agentIcons[agentName]!!),
                            contentDescription = agentName,
                            modifier = Modifier
                                .size(80.dp)
                                .clickable {
                                    agentViewModel.selectAgent(agentName)
                                }
                        )
                    }

                }
            }
            Spacer(modifier = Modifier.height(60.dp))
            if (state.selectedAgents.size == 4) {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF671717)),
                    onClick = {
                        navController.navigate(ScreenRoutes.Result.route)
                    }
                ) {
                    Text("TO RESULT")
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
fun SelectedAgentIcon(
    agentName: String,
    onRemove: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(90.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.BottomCenter,


    ){
        Image(
            painter = painterResource(agentIcons[agentName]!!),
            contentDescription = agentName,
            modifier = Modifier
                .size(90.dp)
                .padding(bottom = 3.dp)
                .clickable { onRemove() }
        )
    }

}
@Composable
fun EmptySlot() {
    Box(
        modifier = Modifier
            .size(90.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
    )
}


