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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator
import cafe.adriel.voyager.transitions.SlideTransition
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.mrpotz.fellowcar.logics.UserRepository
import ru.mrpotz.fellowcar.ui.screens.home.HomeScreen
import ru.mrpotz.fellowcar.ui.screens.onboarding.OnboardingScreen
import ru.mrpotz.fellowcar.ui.screens.root.NavTarget
import ru.mrpotz.fellowcar.ui.theme.FellowCarTheme

class MainViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _currentNavTarget = MutableStateFlow<NavTarget>(NavTarget.None)
    val currentNavTarget: StateFlow<NavTarget>
        get() = _currentNavTarget
    val initialScreen: Flow<NavTarget>
        get() = _currentNavTarget.filter { it != NavTarget.None }.take(1)

    init {
        // try get currently logged user
        viewModelScope.launch {
            val currentUser = userRepository.currentLoggedUser
            launch {
                currentUser.collect {
                    val screenToShow = if (it != null) {
                        // go to main
                        NavTarget.MainScreen
                    } else {
                        NavTarget.Onboarding
                    }
                    _currentNavTarget.value = screenToShow
                }
            }
            val result = userRepository.getCurrentLoggedUser()
            if (result.isFailure) {
                _currentNavTarget.value = NavTarget.Onboarding
            }
        }
    }
}

val LocalScaffoldState: ProvidableCompositionLocal<ScaffoldState> =
    staticCompositionLocalOf { error("no scaffold state was passed") }

fun selectScreen(navTarget: NavTarget?): Screen? {
    return when (navTarget) {
        NavTarget.Onboarding -> OnboardingScreen
        NavTarget.Registration,
        NavTarget.Login,
        NavTarget.None,
        NavTarget.SelectUserRoles,
        -> null
        NavTarget.MainScreen -> HomeScreen
        null -> null
    }
}

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FellowCarTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    val scaffoldState = rememberScaffoldState()
                    CompositionLocalProvider(LocalScaffoldState provides scaffoldState) {
                        val viewModel = viewModel<MainViewModel>(factory = viewModelFactory {
                            initializer {
                                MainViewModel(FellowCarApp.dependencies.userManager)
                            }
                        })

                        Scaffold(
                            scaffoldState = scaffoldState
                        ) { contentPadding ->
                            val initialScreen by viewModel.initialScreen.collectAsState(null)
                            val initial = selectScreen(initialScreen)

                            if (initial != null) {
                                BottomSheetNavigator() {
                                    Navigator(initial) { nav ->
                                        SlideTransition(modifier = Modifier.padding(contentPadding),
                                            navigator = nav
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
