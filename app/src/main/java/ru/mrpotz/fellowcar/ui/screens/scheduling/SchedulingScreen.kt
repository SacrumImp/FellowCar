package ru.mrpotz.fellowcar.ui.screens.scheduling

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen

class SchedulingScreenModel : ScreenModel {
}

object SchedulingScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel<SchedulingScreenModel>() {
            SchedulingScreenModel()
        }

    }
}

@Composable
fun SchedulingScreenComposable() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text("this is scheduling screen", modifier = Modifier.align(Alignment.Center), style = MaterialTheme.typography.h4)
    }
}

@Preview
@Composable
fun SchedulingScreenPreview() {

}
