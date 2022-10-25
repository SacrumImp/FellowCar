package ru.mrpotz.fellowcar.ui.screens.onboarding

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import ru.mrpotz.fellowcar.ui.theme.PrimarySecondary

@Composable
fun ColoredTitleText(blackPart: String, bluePart: String) {
    val annotatedString = buildAnnotatedString {
        append(blackPart)
        withStyle(style = SpanStyle(PrimarySecondary)) {
            append(bluePart)
        }
    }
    val style = MaterialTheme.typography
    Text(text = annotatedString, style = style.h6)
}

@Preview
@Composable
fun ColoredTitleTextPreview() {
    ColoredTitleText(blackPart = "FellowCar", bluePart = "Car")
}
