package ru.mrpotz.fellowcar.ui.screens.pendingride

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.bottomSheet.BottomSheetNavigator

class PendingRideRootScreenModel : ScreenModel {
}

object PendingRideRootScreen : Screen {
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel() {
            PendingRideScreenModel()
        }
        BottomSheetNavigator() {
            PendingRideScreenComposable(
                availableDriversCase = CaseData(caseType = Case.NO_DRIVERS,
                    driversFoundString = null
                ),
            )
        }
    }
}
