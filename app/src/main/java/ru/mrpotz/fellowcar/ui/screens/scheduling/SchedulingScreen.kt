package ru.mrpotz.fellowcar.ui.screens.scheduling

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import ru.mrpotz.fellowcar.models.Request

class SchedulingScreenModel : ScreenModel {
}

object SchedulingScreen : Tab {
    override val options: TabOptions
        @Composable
        get() {
            val vectorPainter = rememberVectorPainter(image = Icons.Default.DirectionsCar)
            return remember {
                TabOptions(
                    index = 0u,
                    title = "Rides",
                    icon = vectorPainter
                )
            }
        }

    @Composable
    override fun Content() {
        val viewModel = rememberScreenModel() {
            SchedulingScreenModel()
        }
        SchedulingScreenComposable()
    }
}

@Composable
fun DateHeader(date: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Text(date, style = MaterialTheme.typography.h6)
        Surface(modifier = Modifier
            .weight(1f)
            .height(2.dp), color = LocalTextStyle.current.color) {

        }
    }
}

fun statusToColor(status: Request.Status): Color {
    return when (status) {
        is Request.Status.Declined -> Color.Red
        Request.Status.InReview -> Color.Yellow
        Request.Status.Planned -> Color.Green
    }
}

fun statusToText(status: Request.Status): String {
    return when (status) {
        is Request.Status.Declined -> "Declined"
        Request.Status.InReview -> "In review"
        Request.Status.Planned -> "Accepted"
    }
}

@Composable
fun RideCard(
    timeString: String,
    requestName: String,
    sentRequests: String,
    status: Request.Status,
) {
    Card() {
        Row() {
            val color = statusToColor(status)

            Surface(modifier = Modifier
                .fillMaxHeight()
                .width(1.dp), color = color) { }
            Column(modifier = Modifier
                .weight(1f)
                .fillMaxHeight()) {
                Text(
                    text = timeString,
                    style = MaterialTheme.typography.body1,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true,
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = requestName, style = MaterialTheme.typography.subtitle1)
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = "requests sent: $sentRequests",
                    style = MaterialTheme.typography.subtitle1)
                Spacer(modifier = Modifier.size(4.dp))
                Text(text = statusToText(status),
                    color = color,
                    style = MaterialTheme.typography.subtitle1)
            }
        }
    }
}

@Preview
@Composable
fun RideCardPreview() {
    RideCard(timeString = "7:00-7:30",
        requestName = "Ride to work",
        sentRequests = "3",
        status = Request.Status.InReview)
}

@Preview
@Composable
fun DateHeaderPreview() {
    DateHeader(date = "Mon 5")
}

@Composable
fun SchedulingScreenComposable() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text("this is scheduling screen stub",
            modifier = Modifier.align(Alignment.Center),
            style = MaterialTheme.typography.h4)
    }
}

@Preview
@Composable
fun SchedulingScreenPreview() {

}
