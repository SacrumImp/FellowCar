package ru.mrpotz.fellowcar.ui.screens.scheduling

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

class SchedulingScreenModel : ScreenModel {
}

object SchedulingScreen : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val vectorPainter = rememberVectorPainter(image = Icons.Default.DirectionsCar)
            return remember {
                TabOptions(
                    index = 0u,
                    title = "Rides",
                    icon = vectorPainter
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel<SchedulingScreenModel>() {
            SchedulingScreenModel()
        }
        SchedulingScreenComposable()
    }
}

@Composable
fun SchedulingScreenComposable() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text("this is scheduling screen stub",
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.h4)
    }
}

@Preview
@Composable
fun SchedulingScreenPreview() {

}
