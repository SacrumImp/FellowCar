package ru.mrpotz.fellowcar.ui.shared

import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun LoadingIndicator() {
    Column(Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        LinearProgressIndicator(Modifier.fillMaxWidth())
        Text(text = "Safely loading your data...",
            style = MaterialTheme.typography.subtitle1,
            fontStyle = FontStyle.Italic)
    }
    Box(modifier = Modifier.fillMaxSize()) {

    }
}

@Preview
@Composable
fun LoadingIndicatorPreview() {
    LoadingIndicator()
}
