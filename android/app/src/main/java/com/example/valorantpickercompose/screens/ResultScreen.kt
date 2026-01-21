package com.example.valorantpickercompose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.valorantpickercompose.viewmodel.AgentViewModel
import com.example.valorantpickercompose.viewmodel.PickerViewModel

@Composable
fun ResultScreen(navController: NavController, pickerViewModel: PickerViewModel,agentViewModel: AgentViewModel) {
    val pickerState = pickerViewModel.state.collectAsState().value
    val agentState = agentViewModel.state.collectAsState().value
    val map: String = pickerState.selectedMap.toString()
    val agents = agentState.selectedAgents.toList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("MAP: $map")
        Spacer(modifier = Modifier.height(20.dp))
        Text(agents.joinToString(", "))
        Button(onClick = {navController.navigateUp()}){
            Text("back")
        }
    }
}