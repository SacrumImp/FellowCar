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
import ru.mrpotz.fellowcar.ui.shared.ImagePlaceholder

class OnboardingViewModel : ScreenModel {
    fun onClickGetStarted() {

    }
}

object OnboardingScreen : Screen {
    @Composable
    override fun Content() {
        val onboardingScreenModel = rememberScreenModel {
            OnboardingViewModel()
        }
        OnboardingScreen(
            onClickGetStarted = onboardingScreenModel::onClickGetStarted
        )
    }
}

@Composable
fun OnboardingScreen(modifier: Modifier = Modifier, onClickGetStarted: () -> Unit) {
    Column(
        modifier = modifier
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .fillMaxHeight()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center) {
            ImagePlaceholder(x = 48.dp)
            Spacer(modifier = Modifier.width(8.dp))
            ColoredTitleText(blackPart = "Fellow", bluePart = "Car")
        }
        Spacer(Modifier.height(24.dp))
        ImagePlaceholder(x = 204.dp, y = 156.dp)
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
