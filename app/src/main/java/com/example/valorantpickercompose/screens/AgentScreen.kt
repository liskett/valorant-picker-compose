package com.example.valorantpickercompose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.valorantpickercompose.PickerViewModel
import com.example.valorantpickercompose.ScreenRoutes


@Composable
fun AgentScreen(navController: NavController,pickerViewModel: PickerViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Choose agents")
        Button(onClick = {
            pickerViewModel.selectAgents(agents = listOf("Sova","Jett"))
            navController.navigate(ScreenRoutes.Result.route)
        }) {
            Text("to result")
        }
        Button(onClick = {navController.navigateUp()}){
            Text("back")
        }
    }
}