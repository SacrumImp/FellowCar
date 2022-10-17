package ru.mrpotz.fellowcar.ui.screens.root

import android.os.UserManager
import android.util.Log
import androidx.compose.runtime.*
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ru.mrpotz.fellowcar.FellowCarApp
import ru.mrpotz.fellowcar.logics.UserRepository
import ru.mrpotz.fellowcar.ui.screens.onboarding.OnboardingScreen

sealed class NavTarget {
    object Onboarding : NavTarget()
    object Registration : NavTarget()
    object Login : NavTarget()
    object None : NavTarget()
    object MainScreen : NavTarget()
    object SelectUserRoles : NavTarget()
    // user selection role for registration
}

//interface ScreenNavigator {
//    fun
//}

class RootViewModel(private val userRepository: UserRepository) : ScreenModel {
    private val _currentNavTarget = MutableStateFlow<NavTarget>(NavTarget.None)
    val currentNavTarget: StateFlow<NavTarget>
        get() = _currentNavTarget

    init {
        // try get currently logged user
        coroutineScope.launch {
            val userResult = userRepository.getCurrentLoggedUser()
            Log.d("RootViewModel", "$userResult")
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

class RootScreen() : Screen {
    @Composable
    override fun Content() {
        val rootViewModel = rememberScreenModel {
            Log.d("RootScreen", "dependencies: ${FellowCarApp.dependencies}")
            RootViewModel(FellowCarApp.dependencies.userManager)
        }
        val currentScreen : NavTarget by rootViewModel.currentNavTarget.collectAsState()

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
            LocalNavigator.currentOrThrow.push(screen)
        }
    }
}
