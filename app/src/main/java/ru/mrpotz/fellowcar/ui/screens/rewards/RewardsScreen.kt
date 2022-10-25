package ru.mrpotz.fellowcar.ui.screens.rewards

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Eco
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

class RewardsScreenModel : ScreenModel {
}

object RewardsScreen : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val vectorPainter = rememberVectorPainter(image = Icons.Default.Eco)
            return remember {
                TabOptions(
                    index = 2u,
                    title = "Rewards",
                    icon = vectorPainter
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel() {
            RewardsScreenModel()
        }
        RewardsScreenComposable()
    }
}

@Composable
fun RewardsScreenComposable() {
    Text(text = "This is rewards screen stub")
}

@Preview
@Composable
fun RewardsScreenPreview() {

}
