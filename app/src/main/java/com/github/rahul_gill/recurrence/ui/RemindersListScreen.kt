package com.github.rahul_gill.recurrence.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.rahul_gill.recurrence.data.database.entities.ReminderEntity
import com.github.rahul_gill.recurrence.utils.IconsUtil
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun RemindersListScreen() {
    val viewModel: AppViewModel = hiltViewModel()
    val reminders = viewModel.activeRemindersList.collectAsState(initial = emptyList())

    LazyColumn{
        items(reminders.value, { it.notificationId }){ reminder ->
            ReminderItem(reminder)
        }
    }
}
@Preview
@Composable
fun ReminderItem(reminderEntity: ReminderEntity = sampleReminder){
    Card(
        modifier = Modifier.padding(8.dp),
        elevation = 2.dp,
    ) {
        Row(Modifier.padding(4.dp)) {
            Image(
                imageVector = IconsUtil.iconsMap[reminderEntity.icon]!!,
                contentDescription = "",
                modifier = Modifier
                    .background(color = Color(reminderEntity.color), shape = CircleShape)
                    .padding(4.dp)
                    .align(CenterVertically),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.surface)
            )
            Column(
                modifier = Modifier
                    .padding(start = 16.dp, top = 4.dp)
                    .weight(1f)
            ) {
                Text(text = reminderEntity.title, style = MaterialTheme.typography.body1, modifier = Modifier.padding(bottom = 4.dp))
                //TODO make it expandable
                Text(text = reminderEntity.content, style = MaterialTheme.typography.caption.copy(color = Color.Gray))
            }
            Text(
                text = reminderEntity.dateTime.format(DateTimeFormatter.ofPattern("hh:mm a")),
                style = MaterialTheme.typography.caption.copy(color = Color.Gray),
                modifier = Modifier.padding(top = 4.dp)

            )
        }
    }
}

val sampleReminder = ReminderEntity(
    notificationId =  1,
    title = "name",
    content = "description",
    dateTime = LocalDateTime.now(),
    repeatType = ReminderEntity.RepetitionType.DOES_NOT_REPEAT,
    foreverState = false,
    numberToShow = 1,
    numberShown = 0,
    icon = "Notifications",
    color = 0xFFFF0000,
    daysOfWeek = 0,
    interval = 0
)