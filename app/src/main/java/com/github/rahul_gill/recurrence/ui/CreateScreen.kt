package com.github.rahul_gill.recurrence.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.rahul_gill.recurrence.R
import com.github.rahul_gill.recurrence.data.database.entities.ReminderEntity
import com.github.rahul_gill.recurrence.ui.components.ColorPicker
import com.github.rahul_gill.recurrence.ui.components.DatePicker
import com.github.rahul_gill.recurrence.ui.components.IconPicker
import com.github.rahul_gill.recurrence.ui.components.TimePicker
import com.github.rahul_gill.recurrence.ui.theme.AppTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


@Destination
@Composable
fun CreateScreen(
    navigator: DestinationsNavigator
) = AppTheme{
    val viewModel: AppViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val time = remember { mutableStateOf(LocalTime.now()) }
    val date = remember { mutableStateOf(LocalDate.now()) }
    var icon by remember { mutableStateOf("Notifications") }
    var color by remember { mutableStateOf(0xFF000000) }

    var timePickerVisible by remember { mutableStateOf(false) }
    var datePickerVisible by remember { mutableStateOf(false) }
    var colorPickerVisible by remember { mutableStateOf(false) }
    var iconPickerVisible by remember { mutableStateOf(false) }

    if(timePickerVisible){
        TimePicker(
            onTimeSelected = { time.value = it },
            onDismissRequest = { timePickerVisible = false }
        )
    }
    if(datePickerVisible){
        DatePicker(
            onDateSelected = { date.value = it },
            onDismissRequest = { datePickerVisible = false }
        )
    }
    if(iconPickerVisible){
        IconPicker(
            onIconSelected = { icon = it },
            onDismissRequest = { iconPickerVisible = false }
        )
    }
    if(colorPickerVisible){
        ColorPicker(
            onColorSelected = { color = it },
            onDismissRequest = { colorPickerVisible = false }
        )
    }

    Column {
        Box(
            modifier = Modifier
                .height(150.dp)
                .background(MaterialTheme.colors.primarySurface)
        ) {

            IconButton(
                onClick = { navigator.navigateUp() },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp),
                content = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = LocalContext.current.getString(R.string.go_back),
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            )
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        viewModel.addReminder(ReminderEntity(
                            notificationId = viewModel.lastNotificationId.first() + 1,
                            title = name,
                            content = description,
                            dateTime = LocalDateTime.of(date.value, time.value),
                            repeatType = ReminderEntity.RepetitionType.DOES_NOT_REPEAT,
                            foreverState = false,
                            numberToShow = 1,
                            numberShown = 0,
                            icon = icon,
                            color =color,
                            daysOfWeek = 0,
                            interval = 0
                        ))
                    }
                    navigator.navigateUp()
                },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
                content = {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = LocalContext.current.getString(R.string.save_menu),
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            )
            TextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(vertical = 16.dp, horizontal = 32.dp)
                    .fillMaxWidth(),
                label = { Text(text = LocalContext.current.getString(R.string.title)) },
                colors = myTextFieldColors()
            )
        }
        Row{
            Icon(
                imageVector = Icons.Filled.Schedule,
                contentDescription = "",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            TextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .padding(horizontal = 22.dp, vertical = 16.dp)
                    .fillMaxWidth(),
                label = { Text(text = LocalContext.current.getString(R.string.content_hint)) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                )
            )
        }
        Divider(thickness = 0.5.dp, color = Color.Gray)
        Row (Modifier
            .clickable { timePickerVisible = true }
            .fillMaxWidth()
        ){
            Icon(
                imageVector = Icons.Filled.Schedule,
                contentDescription = "",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            Text(
                text = LocalContext.current.getString(R.string.action_show_now),
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 16.dp)
            )
        }
        Divider(thickness = 0.5.dp, color = Color.Gray)
        Row (
            Modifier
                .clickable { datePickerVisible = true }
                .fillMaxWidth()
        ){
            Icon(
                imageVector = Icons.Filled.Event,
                contentDescription = "",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            Text(text = LocalContext.current.getString(R.string.date_today),
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 16.dp)
            )
        }
        Divider(thickness = 0.5.dp, color = Color.Gray)
        Row (
            Modifier
                .clickable { iconPickerVisible = true }
                .fillMaxWidth()
        ){
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            Text(text = LocalContext.current.getString(R.string.default_icon),
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 16.dp)
            )
        }
        Divider(thickness = 0.5.dp, color = Color.Gray)
        Row (
            Modifier
                .clickable { colorPickerVisible = true }
                .fillMaxWidth()
        ){
            Icon(
                imageVector = Icons.Default.Palette,
                contentDescription = "",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            Text(text = LocalContext.current.getString(R.string.default_colour),
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 16.dp)
            )
        }
        Divider(thickness = 0.5.dp, color = Color.Gray)
        Row (
            Modifier
                .clickable {

                }
                .fillMaxWidth()
        ){
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            Text(text = LocalContext.current.resources.getStringArray(R.array.repeat_array)[0],
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 16.dp)
            )
        }
        Divider(thickness = 0.5.dp, color = Color.Gray)
    }
}

@Composable
fun myTextFieldColors() = TextFieldDefaults.textFieldColors(
    textColor = MaterialTheme.colors.onPrimary,
    focusedLabelColor = MaterialTheme.colors.onPrimary,
    unfocusedLabelColor =  MaterialTheme.colors.onPrimary,
    backgroundColor = Color.Transparent,
    cursorColor = MaterialTheme.colors.onPrimary,
    unfocusedIndicatorColor = MaterialTheme.colors.onPrimary
)