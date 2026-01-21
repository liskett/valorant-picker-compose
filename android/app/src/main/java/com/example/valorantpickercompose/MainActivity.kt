package com.example.valorantpickercompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.valorantpickercompose.screens.AgentScreen
import com.example.valorantpickercompose.screens.ChooseMapScreen
import com.example.valorantpickercompose.screens.HomeScreen
import com.example.valorantpickercompose.screens.ResultScreen
import com.example.valorantpickercompose.screens.SignInScreen
import com.example.valorantpickercompose.screens.SignUpScreen
import com.example.valorantpickercompose.viewmodel.AgentViewModel
import com.example.valorantpickercompose.viewmodel.AuthViewModel
import com.example.valorantpickercompose.viewmodel.PickerViewModel

sealed class ScreenRoutes(val route: String) {
    object Home : ScreenRoutes("home_screen")
    object SignIn : ScreenRoutes("sign_in_screen")
    object SignUp : ScreenRoutes("sign_up_screen")
    object ChooseMap : ScreenRoutes("choose_map_screen")
    object Agent: ScreenRoutes("agent_screen")
    object Result : ScreenRoutes("result_screen")
}





class MainActivity : ComponentActivity() {
    private val pickerViewModel: PickerViewModel by viewModels()
    private val agentViewModel: AgentViewModel by viewModels()
    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApp(pickerViewModel,agentViewModel, authViewModel)
        }
    }
    override fun onStart() {
        Log.d("TAG","onStart")
        super.onStart()
    }
    override fun onResume() {
        Log.d("TAG","onResume")
        super.onResume()
    }
    override fun onPause() {
        Log.d("TAG","onPause")
        super.onPause()
    }
    override fun onStop() {
        Log.d("TAG","onStop")
        super.onStop()
    }
    override fun onDestroy() {
        Log.d("TAG","onDestroy")
        super.onDestroy()
    }
}
@Composable
fun MyApp(pickerViewModel: PickerViewModel, agentViewModel: AgentViewModel, authViewModel: AuthViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.Home.route
    ) {
        composable(ScreenRoutes.Home.route) { HomeScreen(navController) }
        composable(ScreenRoutes.SignIn.route) { SignInScreen(navController, authViewModel) }
        composable(ScreenRoutes.SignUp.route) { SignUpScreen(navController,authViewModel) }
        composable(ScreenRoutes.ChooseMap.route) { ChooseMapScreen(navController,pickerViewModel) }
        composable(ScreenRoutes.Agent.route) { AgentScreen(navController, agentViewModel) }
        composable(ScreenRoutes.Result.route) { ResultScreen(navController,pickerViewModel,agentViewModel) }
    }
}


