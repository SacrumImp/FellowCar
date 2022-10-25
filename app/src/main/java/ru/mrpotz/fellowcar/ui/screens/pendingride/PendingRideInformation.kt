package ru.mrpotz.fellowcar.ui.screens.pendingride

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
import ru.mrpotz.fellowcar.ui.theme.PrimaryMain

class PendingRideInfoScreenModel : ScreenModel {
}

object PendingRideInfoScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel<PendingRideInfoScreenModel>() {
            PendingRideInfoScreenModel()
        }

    }
}

const val AVAILABLE_DRIVERS = "available drivers"
@Composable
fun PendingRideInfoScreenComposable() {
    val bottomsheetNavigator = LocalBottomSheetNavigator.current
    val normalNavigator = LocalNavigator.currentOrThrow
    Column(Modifier.fillMaxWidth()) {
        Text(text = "Pennding requests", style = MaterialTheme.typography.h5)
        Spacer(Modifier.size(16.dp))
        val annotatedString = buildAnnotatedString {
            append("To arrange a ride, you have to submit a request to one of the drivers from ")
            pushStringAnnotation(tag = AVAILABLE_DRIVERS, annotation = "link")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = PrimaryMain)) {
                append("available drivers")
            }
            pop()
            append(".")
            append("\n")
            append("After you submitted a request to one of ")
            pushStringAnnotation(tag = AVAILABLE_DRIVERS, annotation = "link")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = PrimaryMain)) {
                append("available drivers")
            }
            pop()
            append(", it will appear in Pending Rides section")
        }
        ClickableText(annotatedString, onClick = { offset ->
            // We check if there is an *URL* annotation attached to the text
            // at the clicked position
            annotatedString.getStringAnnotations(tag = AVAILABLE_DRIVERS, start = offset,
                end = offset)
                .firstOrNull()?.let { annotation ->
                    bottomsheetNavigator.pop()
//                    normalNavigator.push("")
                }
        })
    }
}

@Preview
@Composable
fun PendingRideInfoScreenPreview() {

}
