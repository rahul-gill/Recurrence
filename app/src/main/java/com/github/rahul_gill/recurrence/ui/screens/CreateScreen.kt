package com.github.rahul_gill.recurrence.ui.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.rahul_gill.recurrence.R
import com.github.rahul_gill.recurrence.data.database.entities.ReminderEntity
import com.github.rahul_gill.recurrence.data.database.entities.RepetitionType
import com.github.rahul_gill.recurrence.data.database.entities.TimeForDaysOfWeek
import com.github.rahul_gill.recurrence.ui.AppViewModel
import com.github.rahul_gill.recurrence.ui.components.IconPicker
import com.github.rahul_gill.recurrence.ui.destinations.ReminderRepeatBottomSheetDestination
import com.github.rahul_gill.recurrence.ui.destinations.TimeOnWeekDaysPickerDestination
import com.github.rahul_gill.recurrence.ui.theme.AppTheme
import com.github.rahul_gill.recurrence.utils.IconsUtil.getDrawableByName
import com.github.rahul_gill.recurrence.utils.IconsUtil.iconMapX
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.ResultRecipient
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.color.ARGBPickerState
import com.vanpra.composematerialdialogs.color.ColorPalette
import com.vanpra.composematerialdialogs.color.colorChooser
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Destination
@Composable
fun CreateScreen(
    navigator: DestinationsNavigator,
    repetitionTypeResultRecipient: ResultRecipient<ReminderRepeatBottomSheetDestination, RepetitionType>,
    daysOfWeekResultRecipient: ResultRecipient<TimeOnWeekDaysPickerDestination, TimeForDaysOfWeek>
) = AppTheme { Scaffold {
    val context = LocalContext.current
    val viewModel: AppViewModel = hiltViewModel()
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    val time = remember { mutableStateOf(LocalTime.now()) }
    val date = remember { mutableStateOf(LocalDate.now()) }
    var icon by remember { mutableStateOf("Add Alert") }
    var color by remember { mutableStateOf(Color(0xFFF44336)) }
    var timeText by remember { mutableStateOf(context.getString(R.string.action_show_now)) }
    var dateText by remember { mutableStateOf(context.getString(R.string.date_today)) }
    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
    val dateFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy")

    var iconPickerVisible by remember { mutableStateOf(false) }

    val colorDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()
    val dateDialogState = rememberMaterialDialogState()

    var repetitionType by remember { mutableStateOf(RepetitionType.DOES_NOT_REPEAT) }
    var timeForDaysOfWeeks by remember { mutableStateOf(TimeForDaysOfWeek()) }
    var numberOfTimesToRepeat by remember { mutableStateOf(1) }

    repetitionTypeResultRecipient.onResult { repeatType ->
        repetitionType = repeatType
        Log.d("CreateScreen", "new repeat type: $repeatType")
        if (repeatType == RepetitionType.SPECIFIC_DAYS) {
            navigator.navigate(TimeOnWeekDaysPickerDestination)
        }
    }

    daysOfWeekResultRecipient.onResult {
        timeForDaysOfWeeks = it
    }

    MaterialDialog(
        dialogState = colorDialogState,
        buttons = {
            positiveButton(stringResource(id = R.string.ok))
            negativeButton(stringResource(id = R.string.cancel))
        }
    ) {
        Column {
            Text(
                text = LocalContext.current.getString(R.string.select_colour),
                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp, bottom = 16.dp)
            )
            colorChooser(
                colors = ColorPalette.Primary,
                argbPickerState = ARGBPickerState.WithoutAlphaSelector
            ) {
                color = it
            }
        }
    }
    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton(stringResource(id = R.string.ok))
            negativeButton(stringResource(id = R.string.cancel))
        }
    ) {
        timepicker {
            time.value = it
            timeText = it.format(timeFormatter)
        }
    }
    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(stringResource(id = R.string.ok))
            negativeButton(stringResource(id = R.string.cancel))
        }
    ) {
        datepicker {
            date.value = it
            dateText = it.format(dateFormatter)
        }
    }

    if (iconPickerVisible) {
        IconPicker(
            onIconSelected = { icon = it },
            onDismissRequest = { iconPickerVisible = false }
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
                    viewModel.addReminder(
                        ReminderEntity(
                            title = name,
                            content = description,
                            dateTime = LocalDateTime.of(date.value, time.value),
                            foreverState = false,//TODO
                            numberToShow =when(repetitionType) {
                                RepetitionType.DOES_NOT_REPEAT -> 1
                                RepetitionType.SPECIFIC_DAYS -> timeForDaysOfWeeks.size * numberOfTimesToRepeat
                                else -> numberOfTimesToRepeat
                            },
                            numberShown = 0,
                            icon = icon,
                            color = color.toArgb(),
                            repeatType = repetitionType,
                            timeForDaysOfWeek = timeForDaysOfWeeks,
                            interval = 0
                        )
                    )
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
            Column(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(vertical = 16.dp, horizontal = 32.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = LocalContext.current.getString(R.string.title),
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(start = 8.dp)
                )
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    modifier = Modifier.fillMaxWidth(),
                    colors = myTextFieldColors(),
                    textStyle = MaterialTheme.typography.h5
                )
            }
        }
        Row {
            Icon(
                imageVector = Icons.Filled.Segment,
                contentDescription = "",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            TextField(
                value = description,
                onValueChange = { description = it },
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
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
        Row(Modifier
            .clickable {
                timeDialogState.show()
            }
            .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.Schedule,
                contentDescription = "",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            Text(
                text = timeText,
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 16.dp)
            )
        }
        Divider(thickness = 0.5.dp, color = Color.Gray)
        Row(
            Modifier
                .clickable {
                    dateDialogState.show()
                }
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Filled.Event,
                contentDescription = "",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            Text(
                text = dateText,
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 16.dp)
            )
        }
        Divider(thickness = 0.5.dp, color = Color.Gray)
        Row(
            Modifier
                .clickable { iconPickerVisible = true }
                .fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = context.getDrawableByName(iconMapX[icon]!!)),
                contentDescription = "",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            Text(
                text = LocalContext.current.getString(R.string.default_icon),
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 16.dp)
            )
        }
        Divider(thickness = 0.5.dp, color = Color.Gray)
        Row(
            Modifier
                .clickable {
                    colorDialogState.show()
                }
                .fillMaxWidth()
        ) {
            Image(
                imageVector = Icons.Default.Palette,
                contentDescription = "",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
                colorFilter = ColorFilter.tint(color)
            )
            Text(
                text = LocalContext.current.getString(R.string.default_colour),
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 16.dp)
            )
        }
        Divider(thickness = 0.5.dp, color = Color.Gray)
        Row(
            Modifier
                .clickable {
                    navigator.navigate(ReminderRepeatBottomSheetDestination)
                }
                .fillMaxWidth()
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            Text(
                text = LocalContext.current.resources.getStringArray(R.array.repeat_array)[repetitionType.ordinal] +
                    if(repetitionType != RepetitionType.SPECIFIC_DAYS) ""
                    else "\n${timeForDaysOfWeeks.stringify()}",
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 16.dp)
            )
        }
        Divider(thickness = 0.5.dp, color = Color.Gray)
        if(repetitionType != RepetitionType.DOES_NOT_REPEAT) Row {
            Icon(
                imageVector = Icons.Filled.Segment,
                contentDescription = "",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            TextField(
                value = numberOfTimesToRepeat.toString(),
                onValueChange = {
                    numberOfTimesToRepeat = if(it.isBlank()) 1
                        else try {
                            it.toInt()
                    } catch(e: NumberFormatException){
                        //TODO hardcode text
                        android.widget.Toast.makeText(context, "Invalid number", android.widget.Toast.LENGTH_SHORT).show()
                        1
                    }
                },
                modifier = Modifier
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .fillMaxWidth(),
                label = { Text(text = LocalContext.current.getString(R.string.number_of_times_to_repeat)) },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Divider(thickness = 0.5.dp, color = Color.Gray)
    }


}}


@Composable
fun myTextFieldColors() = TextFieldDefaults.textFieldColors(
    textColor = MaterialTheme.colors.onPrimary,
    focusedIndicatorColor = MaterialTheme.colors.onPrimary,
    unfocusedIndicatorColor =  MaterialTheme.colors.onPrimary,
    backgroundColor = Color.Transparent,
    cursorColor = MaterialTheme.colors.onPrimary,
)