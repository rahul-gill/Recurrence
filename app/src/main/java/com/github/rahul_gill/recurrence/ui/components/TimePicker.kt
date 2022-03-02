package com.github.rahul_gill.recurrence.ui.components

import androidx.appcompat.view.ContextThemeWrapper
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.github.rahul_gill.recurrence.R
import java.time.LocalTime

@Composable
fun TimePicker(onTimeSelected: (LocalTime) -> Unit, onDismissRequest: () -> Unit) {
    val selTime = remember { mutableStateOf(LocalTime.now()) }

    Dialog(onDismissRequest = { onDismissRequest() }, properties = DialogProperties()) {
        Column(
            modifier = Modifier
                .wrapContentSize()
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(size = 16.dp)
                )
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
                    .padding(16.dp)
            ) {
                Text(
                    text = LocalContext.current.getString(R.string.select_time_dialog),
                    style = MaterialTheme.typography.caption,
                    color = MaterialTheme.colors.onPrimary
                )
                Spacer(modifier = Modifier.size(24.dp))
            }
            CustomTimePickerView(onTimeSelected = {
                selTime.value = it
            })

            Spacer(modifier = Modifier.size(8.dp))

            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(bottom = 16.dp, end = 16.dp)
            ) {
                TextButton(
                    onClick = onDismissRequest
                ) {
                    Text(
                        text = LocalContext.current.getString(R.string.cancel),
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.primary
                    )
                }

                TextButton(
                    onClick = {
                        onTimeSelected(selTime.value)
                        onDismissRequest()
                    }
                ) {
                    Text(
                        text = LocalContext.current.getString(R.string.ok),
                        style = MaterialTheme.typography.button,
                        color = MaterialTheme.colors.primary
                    )
                }

            }
        }
    }
}

@Composable
fun CustomTimePickerView(onTimeSelected: (LocalTime) -> Unit) {
    AndroidView(
        modifier = Modifier.wrapContentSize(),
        factory = { context ->
            android.widget.TimePicker(ContextThemeWrapper(context, R.style.Dialog))
        },
        update = { view ->
            view.setOnTimeChangedListener{ _, hour, minute ->
                onTimeSelected(
                    LocalTime.now()
                        .withHour(hour)
                        .withMinute(minute)
                )
            }
        }
    )
}