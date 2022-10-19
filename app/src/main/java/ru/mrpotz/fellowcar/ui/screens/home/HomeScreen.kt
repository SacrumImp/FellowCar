package ru.mrpotz.fellowcar.ui.screens.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.CurrentScreen
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.transitions.FadeTransition
import ru.mrpotz.fellowcar.ui.screens.scheduling.SchedulingScreen

class HomeScreenModel : ScreenModel {

}

object HomeScreen : Screen {
    @OptIn(ExperimentalAnimationApi::class)
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel() {
            HomeScreenModel()
        }

        Scaffold(bottomBar = {

        }) {

        }
        Navigator(screen = SchedulingScreen) {
            Column(Modifier.fillMaxSize()) {
                FadeTransition(navigator = it, Modifier.weight(fill = true, weight = 1f)) {
                    CurrentScreen()
                }
                HomeScreenComposable()
            }
        }
    }
}

@Composable
fun HomeScreenComposable() {

}

@Preview
@Composable
fun HomeScreenPreview() {

}
