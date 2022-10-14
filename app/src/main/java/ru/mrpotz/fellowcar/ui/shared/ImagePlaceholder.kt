package ru.mrpotz.fellowcar.ui.shared

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.fade
import com.google.accompanist.placeholder.placeholder
import ru.mrpotz.fellowcar.ui.screens.onboarding.ColoredTitleText

@Composable
fun ImagePlaceholder(x: Dp, y: Dp = x, modifier: Modifier = Modifier) {
    Box(modifier = modifier
        .size(width = x, height = y)
        .placeholder(
            visible = true,
            color = Color.Gray,
            shape = RoundedCornerShape(4.dp),
            highlight = PlaceholderHighlight.fade(highlightColor = Color.LightGray,
                animationSpec = infiniteRepeatable(
                    animation = tween(delayMillis = 300, durationMillis = 1000),
                    repeatMode = RepeatMode.Reverse,
                )))
    )
}

@Preview
@Composable
fun ImagePlaceholderPreview() {
    ImagePlaceholder(x = 64.dp, y = 64.dp)
}

