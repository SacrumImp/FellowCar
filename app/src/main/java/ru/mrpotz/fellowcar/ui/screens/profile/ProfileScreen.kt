package ru.mrpotz.fellowcar.ui.screens.profile

import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import ru.mrpotz.fellowcar.ui.screens.inproduction.UnderDevelopmentScreenComposable

class ProfileScreenModel : ScreenModel {
}

object ProfileScreen : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val vectorPainter = rememberVectorPainter(image = Icons.Default.AccountCircle)
            return remember {
                TabOptions(
                    index = 1u,
                    title = "Profile",
                    icon = vectorPainter
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel() {
            ProfileScreenModel()
        }
        ProfileScreenComposable()

    }
}

@Composable
fun ProfileScreenComposable() {
    UnderDevelopmentScreenComposable(screenTitle = "Profile")
}

@Preview
@Composable
fun ProfileScreenPreview() {

}
