package ru.mrpotz.fellowcar.ui.screens.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.mrpotz.fellowcar.ui.screens.authorization.LoginScreen
import ru.mrpotz.fellowcar.ui.shared.ImagePlaceholder
import ru.mrpotz.fellowcar.R

class OnboardingViewModel(private val navigator: Navigator) : ScreenModel {
    fun onClickGetStarted() {
        navigator.push(LoginScreen)
    }
}

object OnboardingScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val onboardingScreenModel = rememberScreenModel {
            OnboardingViewModel(navigator)
        }
        OnboardingScreen(
            onClickGetStarted = onboardingScreenModel::onClickGetStarted
        )
    }
}

@Composable
fun FellowCarTitleHeader() {
    Row(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center) {
        ImagePlaceholder(x = 48.dp, id = R.drawable.logo)
        Spacer(modifier = Modifier.width(8.dp))
        ColoredTitleText(blackPart = "Fellow", bluePart = "Car")
    }
}

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    onClickGetStarted: () -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        FellowCarTitleHeader()
        Spacer(Modifier.height(24.dp))
        ImagePlaceholder(x = 232.dp, y = 232.dp, id = R.drawable.onbording)
        Spacer(Modifier.height(24.dp))
        Text(text = "Welcome to FellowCar,",
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth())
        Text(text = "Share and schedule rides with fellow workers and save money, time and the environment.",
            textAlign = TextAlign.Start,
            modifier = Modifier.fillMaxWidth())
        Spacer(Modifier.height(24.dp))
        Button(onClick = onClickGetStarted) {
            Text("Get Started", style = MaterialTheme.typography.button)
        }
    }
}

@Preview
@Composable
fun OnboardingScreen() {
    OnboardingScreen {

    }
}
