package ru.mrpotz.fellowcar.ui.screens.inproduction

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.mrpotz.fellowcar.ui.screens.pendingride.AVAILABLE_DRIVERS
import ru.mrpotz.fellowcar.ui.screens.pendingride.SuperText
import ru.mrpotz.fellowcar.ui.theme.PrimarySecondary

class UnderDevelopmentScreenModel : ScreenModel {
}

data class UnderDevelopmentScreen(val screenTitle: String) : Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel<UnderDevelopmentScreenModel>() {
            UnderDevelopmentScreenModel()
        }

    }
}

enum class ButtonActions {
    CheckRepository
}

const val repoUri = """https://github.com/SacrumImp/FellowCar"""

data class InfoBottomSheet(
    val annotatedString: String,
    val buttonText: String,
    val buttonAction: ButtonActions,
    val headline: String,
    val overline: String,
) : Screen {
    @Composable
    override fun Content() {
        Column(Modifier
            .fillMaxWidth()
            .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 24.dp)
            .defaultMinSize(100.dp)) {
            Text(text = overline, style = MaterialTheme.typography.overline)
            Spacer(Modifier.size(8.dp))
            Text(text = headline, style = MaterialTheme.typography.h6)
            Spacer(Modifier.size(16.dp))
            Text(text = annotatedString, /*onClick = {
                // We check if there is an *URL* annotation attached to the text
                // at the clicked position
                annotatedString
            },*/ style = MaterialTheme.typography.body2)
            Spacer(Modifier.size(24.dp))
            val uriHandler = LocalUriHandler.current
            Button(modifier = Modifier.fillMaxWidth(), onClick = {
                when (buttonAction) {
                    ButtonActions.CheckRepository -> uriHandler.openUri(repoUri)
                }
            }) {
                Text(buttonText)
            }
        }
    }
}

@Composable
fun UnderDevelopmentScreenComposable(
    screenTitle: String,
) {
    val bottomSheetNavigator = LocalBottomSheetNavigator.current
    Column(verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)) {
        Column(Modifier
            .fillMaxSize()
            .weight(1f), verticalArrangement = Arrangement.Center) {
            Text(FeatureUnderDevelopment, style = MaterialTheme.typography.overline)
            Spacer(Modifier.size(16.dp))
            Text(screenTitle, style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.size(24.dp))
            Text("""Dear user,
            |you have just found a Feature Under Development! 
            |
            |Our team is eager to implement a beautiful and useful application, stay tuned for any updates!
        """.trimMargin())
        }
        Button(onClick = {
//            val annotatedString = buildAnnotatedString {
//                append()
////                pushStringAnnotation(tag = AVAILABLE_DRIVERS, annotation = "link")
////                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold,
////                    color = PrimarySecondary)) {
////                    append("available drivers")
////                }
////                pop()
////                append(".")
////                append("\n")
////                append("After you submitted a request to one of ")
////                pushStringAnnotation(tag = AVAILABLE_DRIVERS, annotation = "link")
////                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold,
////                    color = PrimarySecondary)) {
////                    append("available drivers")
////                }
////                pop()
////                append(", it will appear in Pending Rides section")
//            }
            val annotatedString =
                "Everything related to the project you can find on our repository and project management tool"
            bottomSheetNavigator.show(
                InfoBottomSheet(annotatedString,
                    overline = FeatureUnderDevelopment,
                    headline = screenTitle,
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
}
const val FeatureUnderDevelopment = "Feature Under Development"

@Preview
@Composable
fun UnderDevelopmentScreenPreview() {

}
