package ru.mrpotz.fellowcar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.SlideTransition
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.mrpotz.fellowcar.logics.UserRepository
import ru.mrpotz.fellowcar.ui.screens.onboarding.OnboardingScreen
import ru.mrpotz.fellowcar.ui.screens.root.NavTarget
import ru.mrpotz.fellowcar.ui.theme.FellowCarTheme

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _currentNavTarget = MutableStateFlow<NavTarget>(NavTarget.None)
    val currentNavTarget: StateFlow<NavTarget>
        get() = _currentNavTarget

    init {
        // try get currently logged user
        viewModelScope.launch {
            val userResult = userRepository.getCurrentLoggedUser()
            val screenToShow = if (userResult.isSuccess) {
                // go to main
                NavTarget.MainScreen
            } else {
                NavTarget.Onboarding
            }
            _currentNavTarget.value = screenToShow
        }
    }
}

val ScaffoldCompositionLocal: ProvidableCompositionLocal<ScaffoldState> = staticCompositionLocalOf { error("no scaffold state was passed") }

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FellowCarTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    val scaffoldState = rememberScaffoldState()
                    CompositionLocalProvider(ScaffoldCompositionLocal provides scaffoldState) {
                        val viewModel = viewModel<MainViewModel>(factory = viewModelFactory {
                            initializer {
                                MainViewModel(FellowCarApp.dependencies.userManager)
                            }
                        })

                        Scaffold(
                            scaffoldState = scaffoldState
                        ) { contentPadding ->
                            val currentScreen by viewModel.currentNavTarget.collectAsState()
                            val screen = when (currentScreen) {
                                NavTarget.Onboarding -> OnboardingScreen
                                NavTarget.Registration,
                                NavTarget.MainScreen,
                                NavTarget.Login,
                                NavTarget.None,
                                NavTarget.SelectUserRoles,
                                -> null
                            }
                            if (screen != null) {
                                Navigator(screen) { navigator ->
                                    SlideTransition(modifier = Modifier.padding(contentPadding),
                                        navigator = navigator)
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}
