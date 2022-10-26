package ru.mrpotz.fellowcar.ui.screens.pendingride

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.bottomSheet.LocalBottomSheetNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import ru.mrpotz.fellowcar.ui.theme.PrimarySecondary

class PendingRideInfoScreenModel : ScreenModel {
}

object PendingRideInfoScreen : Screen {
    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel() {
            PendingRideInfoScreenModel()
        }
        PendingRideInfoScreenComposable()
    }
}

const val AVAILABLE_DRIVERS = "available drivers"

@Composable
fun PendingRideInfoScreenComposable() {
    val bottomsheetNavigator = LocalBottomSheetNavigator.current
    val normalNavigator = LocalNavigator.currentOrThrow
    Column(Modifier
        .fillMaxWidth()
        .padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 24.dp)
        .defaultMinSize(100.dp)) {
        Text(text = "Pending requests", style = MaterialTheme.typography.h5)
        Spacer(Modifier.size(16.dp))
        val annotatedString = buildAnnotatedString {
            append("To arrange a ride, you have to submit a request to one of the drivers from ")
            pushStringAnnotation(tag = AVAILABLE_DRIVERS, annotation = "link")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = PrimarySecondary)) {
                append("available drivers")
            }
            pop()
            append(".")
            append("\n")
            append("After you submitted a request to one of ")
            pushStringAnnotation(tag = AVAILABLE_DRIVERS, annotation = "link")
            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = PrimarySecondary)) {
                append("available drivers")
            }
            pop()
            append(", it will appear in Pending Rides section")
        }
        SuperText(annotatedString, onClick = { offset ->
            // We check if there is an *URL* annotation attached to the text
            // at the clicked position
            annotatedString.getStringAnnotations(tag = AVAILABLE_DRIVERS, start = offset,
                end = offset)
                .firstOrNull()?.let { annotation ->
                    bottomsheetNavigator.hide()
//                    normalNavigator.push("")
                }
        }, style = MaterialTheme.typography.body2)
        Spacer(Modifier.size(24.dp))
        Button(modifier = Modifier.fillMaxWidth(), onClick = { bottomsheetNavigator.hide() }) {
            Text("Continue finding a ride!")
        }
    }
}

@Composable
fun SuperText(
    text: AnnotatedString,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = LocalTextStyle.current,
    onClick: (Int) -> Unit,
) {

    val textColor = color.takeOrElse {
        style.color.takeOrElse {
            LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
        }
    }
    // NOTE(text-perf-review): It might be worthwhile writing a bespoke merge implementation that
    // will avoid reallocating if all of the options here are the defaults
    val mergedStyle = style.merge(
        TextStyle(
            color = textColor,
            fontSize = fontSize,
            fontWeight = fontWeight,
            textAlign = textAlign,
            lineHeight = lineHeight,
            fontFamily = fontFamily,
            textDecoration = textDecoration,
            fontStyle = fontStyle,
            letterSpacing = letterSpacing
        )
    )
    ClickableText(text = text,
        onClick = onClick,
        modifier = modifier,
        style = mergedStyle,
        softWrap = softWrap,
        overflow = overflow,
        maxLines = maxLines,
        onTextLayout = onTextLayout)
}

@Preview
@Composable
fun PendingRideInfoScreenPreview() {

}
