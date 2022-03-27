package com.github.rahul_gill.recurrence.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.rahul_gill.recurrence.R
import com.github.rahul_gill.recurrence.ui.AppViewModel
import com.github.rahul_gill.recurrence.ui.theme.AppTheme
import com.ramcosta.composedestinations.annotation.Destination

@Destination
@Composable
fun SettingsScreen() = AppTheme {
    val viewModel = hiltViewModel<AppViewModel>()

    Column {
        Text(
            text = stringResource(R.string.notification_preferences),
            style = MaterialTheme.typography.caption.copy(color = MaterialTheme.colors.secondary, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(16.dp)
        )

        CheckBoxCard(
            title = "Vibrate",
            isSelected = viewModel.preferencesManager.checkBoxVibrate.collectAsState(initial = false).value,
            onSelectionChange = {
                viewModel.setNotificationProperties(checkBoxVibrate = it)
            }
        )
        Card(
            elevation = 0.dp,
            shape = RectangleShape,
            modifier = Modifier.clickable { /*TODO*/ }
        ) {
            Text(
                text = "Sound",
                style = MaterialTheme.typography.body1,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            )
        }
        Divider(
            thickness = 0.5.dp,
            color = Color.Black,
        )
        CheckBoxCard(
            title = "LED",
            isSelected = viewModel.preferencesManager.checkBoxLed.collectAsState(initial = false).value,
            onSelectionChange = {
                viewModel.setNotificationProperties(checkBoxLed = it)
            }
        )
        CheckBoxCard(
            title = "Persistent",
            isSelected = viewModel.preferencesManager.checkBoxOngoing.collectAsState(initial = false).value,
            onSelectionChange = {
                viewModel.setNotificationProperties(checkBoxOngoing = it)
            }
        )
        CheckBoxCard(
            title = "Mark as Done",
            isSelected = viewModel.preferencesManager.checkBoxMarkAsDone.collectAsState(initial = false).value,
            onSelectionChange = {
                viewModel.setNotificationProperties(checkBoxMarkAsDone = it)
            }
        )
        CheckBoxCard(
            title = "Snooze",
            isSelected = viewModel.preferencesManager.checkBoxSnooze.collectAsState(initial = false).value,
            onSelectionChange = {
                viewModel.setNotificationProperties(checkBoxSnooze = it)
            }
        )
        CheckBoxCard(
            title = "Nagging reminder",
            isSelected = viewModel.preferencesManager.checkBoxNagging.collectAsState(initial = false).value,
            onSelectionChange = {
                viewModel.setNotificationProperties(checkBoxNagging = it)
            }
        )

        Card(
            elevation = 0.dp,
            shape = RectangleShape,
            modifier = Modifier.clickable { /*TODO*/ }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Nagging interval",
                        style = MaterialTheme.typography.body1
                    )
                    val minutes = viewModel.preferencesManager.nagMinutes.collectAsState(initial = 5)
                    val seconds = viewModel.preferencesManager.nagSeconds.collectAsState(initial =0)
                    Text(
                        text = "${minutes.value} minutes ${seconds.value} seconds",
                        style = MaterialTheme.typography.caption.copy(color = Color.DarkGray)
                    )

                }
            }
        }
        Divider(
            thickness = 0.5.dp,
            color = Color.Black,
        )
    }
}

@Composable
fun CheckBoxCard(
    title: String,
    subtitle: String? = null,
    isSelected: Boolean,
    onSelectionChange: (Boolean) -> Unit
){
    Card(
        elevation = 0.dp,
        shape = RectangleShape,
        modifier = Modifier.clickable { onSelectionChange(!isSelected) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp, end = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.body1
                )
                subtitle?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.caption.copy(color = Color.DarkGray)
                    )
                }
            }
            Checkbox(checked = isSelected, onCheckedChange = { onSelectionChange(!isSelected) })
        }
    }
    Divider(
        thickness = 0.5.dp,
        color = Color.Black,
    )
}
