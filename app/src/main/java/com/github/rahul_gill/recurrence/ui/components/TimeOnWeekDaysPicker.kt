package com.github.rahul_gill.recurrence.ui.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.rahul_gill.recurrence.R
import com.github.rahul_gill.recurrence.data.database.entities.TimeForDaysOfWeek
import com.github.rahul_gill.recurrence.ui.theme.AppTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import java.time.DayOfWeek
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Destination(style = DestinationStyle.BottomSheet::class)
@Composable
fun TimeOnWeekDaysPicker(
    resultNavigator: ResultBackNavigator<TimeForDaysOfWeek>
) = AppTheme{

    var weekDayToTimeMap by remember { mutableStateOf( List(7){ LocalTime.now() } ) }
    var checked by remember { mutableStateOf( List(7){ true } ) }
    val weekDayNames = LocalContext.current.weekDaysNames
    val timeFormatter = DateTimeFormatter.ofPattern("hh:mm a")
    val currentTime = LocalTime.now()
    var timeDialogState = rememberMaterialDialogState()
    var timePickerShowing by remember { mutableStateOf(-1) }

    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton(stringResource(id = R.string.ok))
            negativeButton(stringResource(id = R.string.cancel))
        }
    ) {
        timepicker {
            weekDayToTimeMap = weekDayToTimeMap.toMutableList().apply {
                this[timePickerShowing] = it
                timeDialogState.hide()
            }
        }
    }
    Card {
        Column(Modifier.padding(top = 8.dp)) {
            Text(
                text = "Select Reminder time for weekdays",
                Modifier.padding(8.dp)
            )
            for (weekDayIndex in weekDayNames.indices) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp)
                        .clickable {
                            checked = checked
                                .toMutableList()
                                .apply { this[weekDayIndex] = !this[weekDayIndex] }
                        },
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Checkbox(
                        checked = checked[weekDayIndex],
                        onCheckedChange = {
                            checked = checked.toMutableList().apply {
                                this[weekDayIndex] = it
                            }
                        },
                        colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colors.primary)
                    )
                    Text(
                        text = weekDayNames[weekDayIndex],
                        modifier = Modifier
                            .weight(1f)
                    )
                    OutlinedButton(
                        onClick = { timePickerShowing = weekDayIndex; timeDialogState.show() },
                        enabled = checked[weekDayIndex]
                    ) {
                        Text(
                            text = weekDayToTimeMap[weekDayIndex].format(timeFormatter)
                                ?: currentTime.format(timeFormatter)
                        )
                    }
                }
            }
            Button(
                onClick = {
                    resultNavigator.navigateBack(
                        result = TimeForDaysOfWeek().apply {
                            for (i in checked.indices)
                                if (checked[i])
                                    put(DayOfWeek.of(i + 1), weekDayToTimeMap[i])
                        }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(text = "Done")
            }
        }
    }
}

val Context.weekDaysNames: List<String>
    get() = listOf(
        getString(R.string.monday),
        getString(R.string.tuesday),
        getString(R.string.wednesday),
        getString(R.string.thursday),
        getString(R.string.friday),
        getString(R.string.saturday),
        getString(R.string.sunday)
    )