package ru.mrpotz.fellowcar.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import ru.mrpotz.fellowcar.ui.screens.carpoolers.CarpoolersScreen
import ru.mrpotz.fellowcar.ui.screens.profile.ProfileScreen
import ru.mrpotz.fellowcar.ui.screens.rewards.RewardsScreen
import ru.mrpotz.fellowcar.ui.screens.scheduling.SchedulingScreen

class HomeScreenModel : ScreenModel {

}

@Composable
private fun RowScope.TabNavigationItem(tab: Tab) {
    val tabNavigator = LocalTabNavigator.current

    BottomNavigationItem(
        selected = tabNavigator.current == tab,
        onClick = { tabNavigator.current = tab },
        icon = {
            val icon = tab.options.icon
            if (icon != null) {
                Icon(painter = icon, contentDescription = tab.options.title)
            }
        },
        label = {
            Text(tab.options.title)
        }
    )
}

object HomeScreen : Screen {
    @OptIn(ExperimentalMaterialApi::class)
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel() {
            HomeScreenModel()
        }
        TabNavigator(SchedulingScreen) {
            Scaffold(bottomBar = {
                BottomAppBar(
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    TabNavigationItem(tab = SchedulingScreen)
                    TabNavigationItem(tab = CarpoolersScreen)
                    TabNavigationItem(tab = RewardsScreen)
                    TabNavigationItem(tab = ProfileScreen)
                }
            }) {
                Box(modifier = Modifier.padding(it)) {
                    CurrentTab()
                }
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {

}
