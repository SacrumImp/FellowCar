package ru.mrpotz.fellowcar.ui.screens.carpoolers

import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Group
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions

class CarpoolersScreenModel : ScreenModel {
}

object CarpoolersScreen : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val vectorPainter = rememberVectorPainter(image = Icons.Default.Group)
            return remember {
                TabOptions(
                    index = 1u,
                    title = "Carpoolers",
                    icon = vectorPainter
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel<ScreenModel>() {
            CarpoolersScreenModel()
        }
        CarpoolersScreenComposable()
    }
}

@Composable
fun CarpoolersScreenComposable() {
    Text(text = "This is carpoolers screen stub")
}

@Preview
@Composable
fun CarpoolersScreenPreview() {

}
