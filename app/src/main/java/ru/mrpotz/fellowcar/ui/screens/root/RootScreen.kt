package ru.mrpotz.fellowcar.ui.screens.root

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import ru.mrpotz.fellowcar.FellowCarApp
import ru.mrpotz.fellowcar.logics.UserRepository
import ru.mrpotz.fellowcar.ui.screens.home.HomeScreen
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
