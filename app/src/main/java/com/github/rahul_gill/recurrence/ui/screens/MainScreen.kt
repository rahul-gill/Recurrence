package com.github.rahul_gill.recurrence.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.rahul_gill.recurrence.R
import com.github.rahul_gill.recurrence.data.database.entities.ReminderEntity
import com.github.rahul_gill.recurrence.data.database.entities.RepetitionType
import com.github.rahul_gill.recurrence.data.database.entities.TimeForDaysOfWeek
import com.github.rahul_gill.recurrence.ui.AppViewModel
import com.github.rahul_gill.recurrence.ui.components.ExpandableText
import com.github.rahul_gill.recurrence.ui.destinations.CreateScreenDestination
import com.github.rahul_gill.recurrence.ui.destinations.SettingsScreenDestination
import com.github.rahul_gill.recurrence.ui.theme.AppTheme
import com.github.rahul_gill.recurrence.utils.IconsUtil.getDrawableByName
import com.github.rahul_gill.recurrence.utils.IconsUtil.iconMapX
import com.github.rahul_gill.recurrence.utils.argbToColorInt
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


@Destination(start = true)
@Composable
fun MainScreen(navigator: DestinationsNavigator) = AppTheme {
    val viewModel: AppViewModel = hiltViewModel()
    val reminders = viewModel.activeRemindersListGrouped.collectAsState(initial = emptyMap())

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(id = R.string.app_name)) },
                actions = {
                    IconButton(onClick = { navigator.navigate(SettingsScreenDestination) }) {
                        Icon(imageVector = Icons.Default.Settings, contentDescription = "")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navigator.navigate(CreateScreenDestination) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "")
            }
        },
        content = {
            RemindersListScreen(reminders.value)
        }
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RemindersListScreen(remindersMap: Map<LocalDate, List<ReminderEntity>>) {

    LazyColumn{
        remindersMap.forEach { (date, reminders) ->
            stickyHeader {
                Text(
                    text = date.format(DateTimeFormatter.ofPattern("d MMMM yyyy")),
                    style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.primary, fontWeight = FontWeight.Bold),
                    modifier = Modifier
                        .background(color = MaterialTheme.colors.surface.copy(alpha = 0.78f))
                        .padding(start = 4.dp, top = 4.dp)
                )
            }
            items(reminders){ reminder ->
                AnimatedVisibility(
                    visible = reminder in reminders,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    ReminderItem(reminder)
                }
            }
        }
    }
}
@Preview
@Composable
fun ReminderItem(reminderEntity: ReminderEntity = sampleReminder(1)){
    Card(
        modifier = Modifier.padding(vertical =  4.dp, horizontal = 8.dp)
    ) {
        Row(Modifier.padding(4.dp)) {
            Image(
                painter = painterResource(id = LocalContext.current.getDrawableByName(iconMapX[reminderEntity.icon]!!)),
                contentDescription = "",
                modifier = Modifier
                    .background(
                        color = Color(reminderEntity.color.argbToColorInt()),
                        shape = CircleShape
                    )
                    .padding(8.dp)
                    .align(Alignment.Top),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.surface)
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, top = 4.dp)
                    .weight(1f)
            ) {
                Text(text = reminderEntity.title, style = MaterialTheme.typography.body1, modifier = Modifier.padding(bottom = 4.dp))
                ExpandableText(
                    text = reminderEntity.content,
                    style = MaterialTheme.typography.caption.copy(color = Color.Gray))
            }
            Text(
                text = reminderEntity.dateTime.format(DateTimeFormatter.ofPattern("hh:mm a")),
                style = MaterialTheme.typography.caption.copy(color = Color.Gray),
                modifier = Modifier.padding(top = 4.dp)

            )
        }
    }
}

fun sampleReminder(notificationId: Int) = ReminderEntity(
    notificationId =  notificationId,
    title = "name",
    content = "description",
    dateTime = LocalDateTime.now(),
    repeatType = RepetitionType.DOES_NOT_REPEAT,
    foreverState = false,
    numberToShow = 1,
    numberShown = 0,
    icon = "Notifications",
    color = Color.Black.toArgb().argbToColorInt(),
    timeForDaysOfWeek = TimeForDaysOfWeek(),
    interval = 0
)