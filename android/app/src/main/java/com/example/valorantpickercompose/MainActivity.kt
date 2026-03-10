package com.example.valorantpickercompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.valorantpickercompose.data.repository.MapAgentStatsRepository
import com.example.valorantpickercompose.di.AuthModule
import com.example.valorantpickercompose.domain.usecase.RecommendAgentsUseCase
import com.example.valorantpickercompose.screens.AgentScreen
import com.example.valorantpickercompose.screens.ChooseMapScreen
import com.example.valorantpickercompose.screens.HomeScreen
import com.example.valorantpickercompose.screens.ResultScreen
import com.example.valorantpickercompose.screens.SignInScreen
import com.example.valorantpickercompose.screens.SignUpScreen
import com.example.valorantpickercompose.ui.theme.AppTheme
import com.example.valorantpickercompose.viewmodel.AgentViewModel
import com.example.valorantpickercompose.viewmodel.PickerViewModel
import com.example.valorantpickercompose.viewmodel.AuthViewModel
import com.example.valorantpickercompose.domain.model.Recommendation


sealed class ScreenRoutes(val route: String) {
    object Home : ScreenRoutes("home_screen")
    object SignIn : ScreenRoutes("sign_in_screen")
    object SignUp : ScreenRoutes("sign_up_screen")
    object ChooseMap : ScreenRoutes("choose_map_screen")
    object Agent : ScreenRoutes("agent_screen")
    object Result : ScreenRoutes("result_screen")
}

class MainActivity : ComponentActivity() {

    private val pickerViewModel: PickerViewModel by viewModels()
    private val agentViewModel: AgentViewModel by viewModels()
    // AuthViewModel будем создавать через AuthModule внутри MyApp

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                MyApp(pickerViewModel, agentViewModel)
            }

        }
    }

    override fun onStart() {
        Log.d("TAG", "onStart")
        super.onStart()
    }
    override fun onResume() {
        Log.d("TAG", "onResume")
        super.onResume()
    }
    override fun onPause() {
        Log.d("TAG", "onPause")
        super.onPause()
    }
    override fun onStop() {
        Log.d("TAG", "onStop")
        super.onStop()
    }
    override fun onDestroy() {
        Log.d("TAG", "onDestroy")
        super.onDestroy()
    }
}

@Composable
fun MyApp(
    pickerViewModel: PickerViewModel,
    agentViewModel: AgentViewModel
) {
    val navController = rememberNavController()
    val authViewModel: AuthViewModel = androidx.compose.runtime.remember {
        AuthModule.provideAuthViewModel()
    }
    //for SignInScreen
    val loginState by authViewModel.loginState.collectAsState()
    var signInEmail by remember { mutableStateOf("") }
    var signInPassword by remember { mutableStateOf("") }

    //for SignUpScreen
    val registerState by authViewModel.registerState.collectAsState()
    var signUpEmail by remember { mutableStateOf("") }
    var signUpPassword by remember { mutableStateOf("") }

    //for ChooseMapScreen
    val pickerState by pickerViewModel.state.collectAsState()

    //for AgentScreen
    val agentState by agentViewModel.state.collectAsState()

    //for ResultScreen
    val context = LocalContext.current

    val mapStatsRepository = remember {
        MapAgentStatsRepository(context)
    }

    val recommendAgentsUseCase = remember {
        RecommendAgentsUseCase(
            mapStatsRepository = mapStatsRepository
        )
    }

    val pickerStateValue = pickerViewModel.state.collectAsState().value
    val agentStateValue = agentViewModel.state.collectAsState().value
    val selectedMap: String = pickerStateValue.selectedMap ?: ""
    val selectedAgents = agentStateValue.selectedAgents.toList()

    val recommendation: Recommendation = remember(selectedMap, selectedAgents) {
        if (selectedMap.isBlank()) {
            Recommendation(emptyList(), emptyList())
        } else {
            recommendAgentsUseCase.getRecommendationForMap(
                mapName = selectedMap,
                selectedAgents = selectedAgents
            )
        }
    }



    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.Home.route
    ) {
        composable(ScreenRoutes.Home.route) {
            HomeScreen(
                onSignInClick = {navController.navigate(ScreenRoutes.SignIn.route)}
            )
        }
        composable(ScreenRoutes.SignIn.route) {
            SignInScreen(
                onSignUpClick = {navController.navigate(ScreenRoutes.SignUp.route)},
                onBackClick = { navController.popBackStack() },
                moveToChooseMapScreen = {
                    navController.navigate(ScreenRoutes.ChooseMap.route) {
                        popUpTo(ScreenRoutes.Home.route) { inclusive = true }
                    }
                },
                onSignInClick = { authViewModel.login(signInEmail, signInPassword) },
                loginState = loginState,
                email = signInEmail,
                onEmailChange = {signInEmail = it},
                password = signInPassword,
                onPasswordChange = {signInPassword = it}
            )
        }
        composable(ScreenRoutes.SignUp.route) {
            SignUpScreen(
                onBackClick = {navController.popBackStack()},
                moveToSignInScreen = {navController.navigate(ScreenRoutes.SignIn.route)},
                onSignUpClick = { authViewModel.register(signUpEmail, signUpPassword) },
                registerState = registerState,
                email = signUpEmail,
                onEmailChange = {signUpEmail = it},
                password = signUpPassword,
                onPasswordChange = {signUpPassword = it},
            )
        }
        composable(ScreenRoutes.ChooseMap.route) {
            ChooseMapScreen(
                selectedMap = pickerState.selectedMap,
                onMapClick = { mapName ->
                    pickerViewModel.selectMap(mapName)
                    navController.navigate(ScreenRoutes.Agent.route)
                }
            )
        }
        composable(ScreenRoutes.Agent.route) {
            AgentScreen(
                agentState = agentState,
                onSelectAgent = { name ->
                    if (agentState.selectedAgents.contains(name)) {
                        agentViewModel.removeAgent(name)
                    } else {
                        agentViewModel.selectAgent(name)
                    }
                },
                onRemoveAgent = { name -> agentViewModel.removeAgent(name) },
                onToResultClick = {navController.navigate(ScreenRoutes.Result.route)},
                onBackClick = {navController.popBackStack()}
            )
        }
        composable(ScreenRoutes.Result.route) {
            ResultScreen(
                onBackClick = {navController.popBackStack()},
                selectedMap = selectedMap,
                selectedAgents = selectedAgents,
                recommendation = recommendation
            )
        }
    }
}
