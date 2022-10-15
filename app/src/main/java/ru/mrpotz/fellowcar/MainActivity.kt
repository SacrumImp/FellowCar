package ru.mrpotz.fellowcar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Modifier
import androidx.navigation.Navigation
import cafe.adriel.voyager.navigator.Navigator
import ru.mrpotz.fellowcar.ui.screens.onboarding.OnboardingScreen
import ru.mrpotz.fellowcar.ui.theme.FellowCarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FellowCarTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    Navigator(OnboardingScreen)

                }
            }
        }
    }
}
