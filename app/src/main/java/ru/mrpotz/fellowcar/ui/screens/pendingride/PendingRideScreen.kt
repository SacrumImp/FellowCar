package ru.mrpotz.fellowcar.ui.screens.pendingride

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Help
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.End
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.mrpotz.fellowcar.LocalScaffoldState
import ru.mrpotz.fellowcar.ui.theme.DeclinedColor
import ru.mrpotz.fellowcar.ui.theme.PendingColor
import ru.mrpotz.fellowcar.ui.theme.SuccessColor

class PendingRideScreenModel : ScreenModel {
}

object PendingRideScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel() {
            PendingRideScreenModel()
        }
        PendingRideScreenComposable(
            availableDriversCase = CaseData(caseType = Case.NO_DRIVERS,
                driversFoundString = null
            ),
        )
    }
}

enum class Case {
    NO_DRIVERS,
    DRIVERS_AVAILABLE;

    fun toCardSubtitle(): String {
        return when (this) {
            NO_DRIVERS -> "Looking for your drivers..."
            DRIVERS_AVAILABLE -> "Found available drivers"
        }
    }

}

data class CaseData(val caseType: Case, val driversFoundString: String?) {
    fun loadingSection(): String {
        return when (caseType) {
            Case.NO_DRIVERS -> caseType.toCardSubtitle()
            Case.DRIVERS_AVAILABLE -> driversFoundString!!
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DriversCard(
    availableDriversCase: CaseData,
) {
    val current = LocalScaffoldState.current
    val coroutineScope = rememberCoroutineScope()
    Card(shape = MaterialTheme.shapes.medium.copy(CornerSize(8.dp)), elevation = 4.dp,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp), onClick = {

        }) {
        Column(modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Available Drivers",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .alignByBaseline())
            }
            Spacer(modifier = Modifier.size(4.dp))

            Text(
                text = "The drivers with similar rides",
                style = MaterialTheme.typography.subtitle1.let {
                    it.copy(color = it.color.copy(alpha = 0.6f))
                },
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                softWrap = true,
            )
            Spacer(modifier = Modifier.size(16.dp))
            Divider()
            Spacer(modifier = Modifier.size(16.dp))
            Row {
                Text(
                    text = availableDriversCase.loadingSection(),
                    style = MaterialTheme.typography.body2.let {
                        it.copy(color = it.color.copy(alpha = 0.6f))
                    },
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true,
                )
                if (availableDriversCase.caseType == Case.NO_DRIVERS) {
                    Spacer(modifier = Modifier.size(8.dp))
                    CircularProgressIndicator(strokeWidth = 1.dp,
                        modifier = Modifier
                            .size(12.dp)
                            .align(CenterVertically))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PendingRequestsCard() {
    val snackbar = LocalScaffoldState.current
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetNavigator = LocalBottomSheetNavigator.current
    Card(shape = MaterialTheme.shapes.medium.copy(CornerSize(8.dp)), elevation = 4.dp,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp), onClick = {

        }) {
        Column(modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Pending requests",
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .alignByBaseline())
            }
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = "Requests you sent to drivers for their rides",
                style = MaterialTheme.typography.subtitle1.let {
                    it.copy(color = it.color.copy(alpha = 0.6f))
                },
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                softWrap = true,
            )
            Spacer(modifier = Modifier.size(12.dp))
            Divider()
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = "Current status",
                style = MaterialTheme.typography.body2.let {
                    it.copy(color = it.color.copy(alpha = 0.6f))
                },
                textAlign = TextAlign.Start,
                overflow = TextOverflow.Ellipsis,
                softWrap = true,
            )

            Row() {
                Column {

                }
            }
            TextWithStatusSign(text = "Accepted: ", color = SuccessColor)x
            TextWithStatusSign(text = "In review: ", color = PendingColor)
            TextWithStatusSign(text = "Declined: ", color = DeclinedColor)

            IconButton(onClick = { bottomSheetNavigator.push(PendingRideInfoScreen) },
                modifier = Modifier.align(End)) {
                Icon(painter = rememberVectorPainter(image = Icons.Filled.Help),
                    contentDescription = "Help")
            }
        }
    }
}

@Composable
fun TextWithStatusSign(
    text: String,
    color: Color,
    value : String
) {
    Column() {
        Row {
            Surface(modifier = Modifier
                .padding(top = 2.dp)
                .size(8.dp)
                .align(CenterVertically),
                shape = CircleShape,
                color = color) { }
            Spacer(modifier = Modifier
                .size(2.dp)
                .align(CenterVertically))
            Text(
                modifier = Modifier
                    .padding(start = 4.dp,
                        end = 4.dp,
                        top = 2.dp,
                        bottom = 2.dp)
                    .alignByBaseline(),
                text = text,
                style = MaterialTheme.typography.body2)
        }

    }
}

@Composable
fun PendingRideScreenComposable(availableDriversCase: CaseData) {
    val navigator = LocalNavigator.currentOrThrow
    Scaffold(topBar = {
        TopAppBar(modifier = Modifier.fillMaxWidth()) {
            Icon(
                imageVector = Icons.Filled.ArrowBack,
                contentDescription = "Back",
                modifier = Modifier
                    .padding(16.dp)
                    .clickable {
                        navigator.pop()
                    }
            )
//            Spacer(modifier = Modifier.size(16.dp))
            Text(text = "Ride to Home, 7:00-7:30", style = MaterialTheme.typography.h6)
        }
    }) { it ->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(it)) {
            Spacer(modifier = Modifier.size(24.dp))
            Column(modifier = Modifier.weight(1f)) {
                Spacer(modifier = Modifier.size(24.dp))
                DriversCard(availableDriversCase = availableDriversCase)
            }
        }
    }
}

@Preview
@Composable
fun PendingRideScreenPreview() {
    DriversCard(availableDriversCase = CaseData(caseType = Case.NO_DRIVERS, null))
}
