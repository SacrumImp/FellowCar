package ru.mrpotz.fellowcar.ui.screens.scheduling

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.rememberScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import ru.mrpotz.fellowcar.models.Request
import ru.mrpotz.fellowcar.ui.screens.pendingride.PendingRideScreen
import ru.mrpotz.fellowcar.ui.theme.DeclinedColor
import ru.mrpotz.fellowcar.ui.theme.PendingColor
import ru.mrpotz.fellowcar.ui.theme.SuccessColor

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
    Row(modifier = Modifier
        .padding(start = 16.dp, end = 16.dp)
        .fillMaxWidth()) {
        Text(date, style = MaterialTheme.typography.h5, color = MaterialTheme.colors.primary)
        Spacer(modifier = Modifier.width(8.dp))
    }
}

fun statusToColor(status: Request.Status): Color {
    return when (status) {
        is Request.Status.Declined -> DeclinedColor
        Request.Status.InReview -> PendingColor
        Request.Status.Planned -> SuccessColor
    }
}

fun statusToText(status: Request.Status): String {
    return when (status) {
        is Request.Status.Declined -> "Declined"
        Request.Status.InReview -> "Pending"
        Request.Status.Planned -> "Accepted"
    }
}

// design ideas for status https://dribbble.com/shots/14899708-Report-Task-Plan-Dashboard
// suggestion for usability https://dribbble.com/shots/6770872-Travel-Trip-Approval
//

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun RideCard(
    timeString: String,
    requestName: String,
    sentRequests: String = " ",
    status: Request.Status,
) {
    val localNavigator = (LocalNavigator.currentOrThrow.parent)!!
    Card(shape = MaterialTheme.shapes.medium.copy(CornerSize(8.dp)), elevation = 4.dp,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp), onClick = {
            localNavigator.push(PendingRideScreen)
        }) {
        Column(modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)) {
            Row(verticalAlignment = CenterVertically) {

                Text(text = requestName,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier
                        .alignByBaseline())
            }
            Spacer(modifier = Modifier.size(4.dp))
            Row {
                Text(
                    text = timeString,
                    style = MaterialTheme.typography.subtitle1,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis,
                    softWrap = true,
                )
            }
            Spacer(modifier = Modifier.size(4.dp))
            Spacer(modifier = Modifier.size(4.dp))
            Text(style = MaterialTheme.typography.body2,
                text = "From Pokrovskaya,11 to Stavropolskaya, 5, meeting at D exit")
            Spacer(modifier = Modifier.size(8.dp))
            Divider()
            Spacer(modifier = Modifier.size(8.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    "Request status:",
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.Bold,
                )
                Spacer(Modifier.size(12.dp))
                Surface(modifier = Modifier
                    .padding(top = 2.dp)
                    .size(8.dp)
                    .align(CenterVertically),
                    shape = CircleShape,
                    color = statusToColor(status)) { }
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
                    text = statusToText(status),
                    style = MaterialTheme.typography.body2)
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
        status = Request.Status.Planned)
}

@Preview
@Composable
fun DateHeaderPreview() {
    DateHeader(date = "Mon 5")
}

val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

@Composable
fun SchedulingScreenComposable() {
    val lazyColumnState = rememberLazyListState()
    LazyColumn(modifier = Modifier.fillMaxSize(),
        state = lazyColumnState,
        contentPadding = PaddingValues(top = 24.dp, bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)) {
        for (i in 0..100) {
            val dateText = days[i % 7]
            val dateNumber = (i + 1) % 32
            val dateString = "$dateText $dateNumber"
            item {
                DateHeader(date = dateString)
            }
            item {
                RideCard(timeString = "7:00 - 7:30",
                    requestName = "Ride to Home",
                    status = Request.Status.InReview)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Preview
@Composable
fun SchedulingScreenPreview() {

}
