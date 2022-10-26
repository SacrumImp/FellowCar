package ru.mrpotz.fellowcar.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.CurrentTab
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import ru.mrpotz.fellowcar.ui.screens.carpoolers.CarpoolersScreen
import ru.mrpotz.fellowcar.ui.screens.profile.ProfileScreen
import ru.mrpotz.fellowcar.ui.screens.rewards.RewardsScreen
import ru.mrpotz.fellowcar.ui.screens.ridecreation.CreateRideScreen
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
            Text(tab.options.title, softWrap = false)
        }
    )
}

object HomeScreen : Screen {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel() {
            HomeScreenModel()
        }
        val navigator = LocalNavigator.currentOrThrow
        TabNavigator(SchedulingScreen) {
            Scaffold(bottomBar = {
                BottomAppBar(
                    modifier = Modifier.fillMaxWidth(),
//                    cutoutShape = RoundedCornerShape(25)//RoundedCornerShape(8.dp)
                ) {
                    TabNavigationItem(tab = SchedulingScreen)
                    TabNavigationItem(tab = CarpoolersScreen)
                    TabNavigationItem(tab = RewardsScreen)
                    TabNavigationItem(tab = ProfileScreen)
                }
            },
                isFloatingActionButtonDocked = false,
                floatingActionButtonPosition = FabPosition.End,
                floatingActionButton = {
                    if (it.current == SchedulingScreen) {
                        FloatingActionButton(onClick = { navigator.push(CreateRideScreen) }, shape = RoundedCornerShape(25)) {
                            Icon(painter = rememberVectorPainter(image = Icons.Outlined.Add),
                                contentDescription = "New Ride")
                        }
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
