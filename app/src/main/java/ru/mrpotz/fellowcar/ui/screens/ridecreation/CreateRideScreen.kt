package ru.mrpotz.fellowcar.ui.screens.ridecreation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.EditNote
import androidx.compose.material.icons.outlined.Input
import androidx.compose.material.icons.outlined.Map
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import kotlinx.coroutines.launch
import ru.mrpotz.fellowcar.ui.screens.authorization.ErrorableTextField
import ru.mrpotz.fellowcar.ui.screens.authorization.PasswordErrorableTextField
import ru.mrpotz.fellowcar.ui.screens.inproduction.ButtonActions
import ru.mrpotz.fellowcar.ui.screens.inproduction.FeatureUnderDevelopment
import ru.mrpotz.fellowcar.ui.screens.inproduction.InfoBottomSheet

class CreateRideScreenModel : ScreenModel {
}

object CreateRideScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel() {
            CreateRideScreenModel()

        }
        CreateRideScreenComposable()
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreateRideScreenComposable() {
    val navigator = LocalNavigator.currentOrThrow
    val scaffoldState = rememberBackdropScaffoldState(initialValue = BackdropValue.Concealed)
    val scope = rememberCoroutineScope()

    BackdropScaffold(
        appBar = {
            TopAppBar(
                title = {
                    Text("Creating ride")
                },
                navigationIcon = {
                    if (scaffoldState.isConcealed) {
                        IconButton(
                            onClick = {
                                scope.launch { scaffoldState.reveal() }
                            }
                        ) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Menu"
                            )
                        }
                    } else {
                        IconButton(
                            onClick = {
                                scope.launch { scaffoldState.conceal() }
                            }
                        ) {
                            Icon(
                                Icons.Default.Close,
                                contentDescription = "Close"
                            )
                        }
                    }
                },
                elevation = 0.dp,
//                backgroundColor = Color.Transparent
            )
        },
        backLayerContent = {
            val bottomSheetNavigator = LocalBottomSheetNavigator.current
            Column(verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp).fillMaxSize(),) {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().weight(1f)) {
                    Icon(modifier = Modifier
                        .size(64.dp)
                        .alpha(0.6f),
                        painter = rememberVectorPainter(image = Icons.Outlined.Map),
                        contentDescription = "Map")
                    Spacer(modifier = Modifier.size(24.dp))
                    Text(modifier = Modifier.alpha(0.6f).fillMaxWidth().align(CenterHorizontally),
                        textAlign = TextAlign.Center,
                        text = "Here you will be able to review your ride information and view details on map",
                        style = MaterialTheme.typography.subtitle1
                    )
                }
                OutlinedButton(onClick = {
                    val annotatedString =
                        "Everything related to the project you can find on our repository and project management tool"
                    bottomSheetNavigator.show(
                        InfoBottomSheet(annotatedString,
                            overline = FeatureUnderDevelopment,
                            headline = "Map and review",
                            buttonText = "Check repository",
                            buttonAction = ButtonActions.CheckRepository)
                    )
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    /*.height(48.dp)*/) {
                    Text(text = "Learn more")
                }
            }
        },     // Defaults to BackdropScaffoldDefaults.PeekHeight
        frontLayerContent = {
            val bottomSheetNavigator = LocalBottomSheetNavigator.current
            Column(verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp).fillMaxSize()) {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().weight(1f).fillMaxWidth()) {
                    Icon(modifier = Modifier
                        .size(64.dp)
                        .alpha(0.6f),
                        painter = rememberVectorPainter(image = Icons.Outlined.EditNote),
                        contentDescription = "Chat Bubble")
                    Spacer(modifier = Modifier.size(24.dp))
                    Text(modifier = Modifier.alpha(0.6f).fillMaxWidth().align(CenterHorizontally),
                        textAlign = TextAlign.Center,
                        text = "Here you will be able to edit your ride information",
                        style = MaterialTheme.typography.subtitle1)
                }
                Button(onClick = {
                    val annotatedString =
                        "Everything related to the project you can find on our repository and project management tool"
                    bottomSheetNavigator.show(
                        InfoBottomSheet(annotatedString,
                            overline = FeatureUnderDevelopment,
                            headline = "Editing ride info",
                            buttonText = "Check repository",
                            buttonAction = ButtonActions.CheckRepository)
                    )
                }, modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp)
                    /*.height(48.dp)*/) {
                    Text(text = "Learn more")
                }
            }
        },
        peekHeight = 40.dp,
        // Defaults to BackdropScaffoldDefaults.HeaderHeight
        headerHeight = 60.dp,
    )
}

@Composable
fun PlannedRidePlaceholder() {
    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
        Icon(modifier = Modifier
            .size(64.dp)
            .alpha(0.6f),
            painter = rememberVectorPainter(image = Icons.Outlined.ChatBubbleOutline),
            contentDescription = "Chat Bubble")
        Spacer(modifier = Modifier.size(24.dp))
        Text(modifier = Modifier.alpha(0.6f), text = "You haven't agreed with any drivers yet",
            style = MaterialTheme.typography.subtitle1)
    }
}

@Preview
@Composable
fun CreateRideScreenPreview() {

}
