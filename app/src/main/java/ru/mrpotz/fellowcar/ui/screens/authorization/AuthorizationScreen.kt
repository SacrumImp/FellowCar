package ru.mrpotz.fellowcar.ui.screens.authorization

import android.os.UserManager
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.coroutineScope
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import ru.mrpotz.fellowcar.FellowCarApp
import ru.mrpotz.fellowcar.logics.UserRepository

class AuthorizationScreenModel(val userRepository: UserRepository) : ScreenModel {


    init {
        coroutineScope
    }
}

class AuthorizationScreen() : Screen {
    @Composable
    override fun Content() {
        val model = rememberScreenModel {
            AuthorizationScreenModel(FellowCarApp.dependencies.userManager)
        }

    }
}
