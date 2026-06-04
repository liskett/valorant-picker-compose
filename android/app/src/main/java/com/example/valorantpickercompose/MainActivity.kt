package com.example.valorantpickercompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalUriHandler
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.valorantpickercompose.di.AppModule
import com.example.valorantpickercompose.presentation.screens.AgentScreen
import com.example.valorantpickercompose.presentation.screens.ChooseMapScreen
import com.example.valorantpickercompose.presentation.screens.HomeScreen
import com.example.valorantpickercompose.presentation.screens.ResultScreen
import com.example.valorantpickercompose.presentation.screens.SettingsScreen
import com.example.valorantpickercompose.presentation.screens.SignInScreen
import com.example.valorantpickercompose.presentation.screens.SignUpScreen
import com.example.valorantpickercompose.presentation.ui.theme.AppTheme
import com.example.valorantpickercompose.presentation.viewmodel.PickerViewModel
import com.example.valorantpickercompose.presentation.viewmodel.AuthViewModel
import com.example.valorantpickercompose.presentation.viewmodel.SettingsViewModel

// sealed класс для хранения маршрутов навигации
sealed class ScreenRoutes(val route: String) {
    object Home : ScreenRoutes("home_screen")
    object SignIn : ScreenRoutes("sign_in_screen")
    object SignUp : ScreenRoutes("sign_up_screen")
    object ChooseMap : ScreenRoutes("choose_map_screen")
    object Agent : ScreenRoutes("agent_screen")
    object Result : ScreenRoutes("result_screen")
    object Settings : ScreenRoutes("settings_screen")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppModule.init(this)
        setContent {
            AppTheme {
                MyApp()
            }
        }
    }
    // логирование жизненного цикла
    override fun onStart() {
        Log.d("TAG", "onStart")
        super.onStart()
    }
    override fun onResume() {
        Log.d("TAG", "onResume")
        super.onResume()
        enableEdgeToEdge()
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
fun MyApp() {
    // remember чтобы не пересоздавался при рекомпозициях
    val navController = rememberNavController()
    // создаем ViewModel через DI-модуль
    val authViewModel: AuthViewModel = viewModel(
        factory = AppModule.provideAuthViewModelFactory()
    )
    val pickerViewModel: PickerViewModel = viewModel(
        factory = AppModule.providePickerViewModelFactory()
    )
    // for SignInScreen
    val loginState by authViewModel.loginState.collectAsStateWithLifecycle() // подписываемся на состояние входа
    var signInEmail by remember { mutableStateOf("") } // состояние email при заходе на экран
    var signInPassword by remember { mutableStateOf("") } // состояние password при заходе на экран

    // for SignUpScreen
    val registerState by authViewModel.registerState.collectAsStateWithLifecycle() // подписываемся на состояние регистрации
    var signUpEmail by remember { mutableStateOf("") } // состояние email при заходе на экран
    var signUpPassword by remember { mutableStateOf("") } // состояние password при заходе на экран

    // for ChooseMapScreen и AgentScreen, получаем данные из PickerViewModel
    val pickerState by pickerViewModel.state.collectAsStateWithLifecycle() // подписываемся на состояние выбора

    val selectedMap = pickerState.selectedMap
    val selectedAgents = pickerState.selectedAgents

    // навигация: начало - HomeScreen
    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.Home.route
    ) {
        composable(ScreenRoutes.Home.route) {
            val uriHandler = LocalUriHandler.current
            HomeScreen(
                onSignInClick = {
                    navController.navigate(ScreenRoutes.SignIn.route)
                },
                onSettingsClick = {
                    navController.navigate(ScreenRoutes.Settings.route)
                },
                onContactsClick = {
                    uriHandler.openUri("https://t.me/dyqehx")
                }
            )
        }

        composable(ScreenRoutes.SignIn.route) {
            SignInScreen(
                onSignUpClick = { navController.navigate(ScreenRoutes.SignUp.route) },
                onBackClick = { navController.navigate(ScreenRoutes.Home.route) },
                moveToChooseMapScreen = {
                    // при успешном входе переходим на выбор карты
                    // удаляем экраны входа и регистрации из стека
                    navController.navigate(ScreenRoutes.ChooseMap.route) {
                        popUpTo(ScreenRoutes.Home.route) { inclusive = true }
                    }
                },
                onSignInClick = { authViewModel.login(signInEmail, signInPassword) },
                loginState = loginState,
                email = signInEmail,
                onEmailChange = { signInEmail = it },
                password = signInPassword,
                onPasswordChange = { signInPassword = it }
            )
        }

        composable(ScreenRoutes.SignUp.route) {
            SignUpScreen(
                onBackClick = { navController.popBackStack() },
                moveToSignInScreen = {
                    navController.navigate(ScreenRoutes.SignIn.route) {
                        popUpTo(ScreenRoutes.SignUp.route) {
                            inclusive = true
                        }
                    }
                },
                onSignUpClick = { authViewModel.register(signUpEmail, signUpPassword) },
                registerState = registerState,
                email = signUpEmail,
                onEmailChange = { signUpEmail = it },
                password = signUpPassword,
                onPasswordChange = { signUpPassword = it },
                onRegisterHandled = {
                    authViewModel.resetRegisterState()
                    signUpEmail = ""
                    signUpPassword = ""
                }
            )
        }

        composable(ScreenRoutes.ChooseMap.route) {
            ChooseMapScreen(
                selectedMap = pickerState.selectedMap,
                onMapClick = { map ->
                    pickerViewModel.selectMap(map)
                    navController.navigate(ScreenRoutes.Agent.route)
                }
            )
        }

        composable(ScreenRoutes.Agent.route) {
            AgentScreen(
                pickerState = pickerState,
                onSelectAgent = { name ->
                    if (pickerState.selectedAgents.contains(name)) {
                        pickerViewModel.removeAgent(name)
                    } else {
                        pickerViewModel.selectAgent(name)
                    }
                },
                onRemoveAgent = { name -> pickerViewModel.removeAgent(name) },
                onToResultClick = { navController.navigate(ScreenRoutes.Result.route) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(ScreenRoutes.Result.route) {
            ResultScreen(
                onBackClick = { navController.popBackStack() },
                selectedMap = selectedMap,
                selectedAgents = selectedAgents,
                viewModel = pickerViewModel
            )
        }
        composable(ScreenRoutes.Settings.route) {
            val settingsViewModel: SettingsViewModel = viewModel(
                factory = AppModule.provideSettingsViewModelFactory()
            )
            SettingsScreen(
                viewModel = settingsViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}