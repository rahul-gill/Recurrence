package com.github.rahul_gill.recurrence.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.rahul_gill.recurrence.ui.theme.AppTheme

@Preview(showBackground = true)
@Composable
fun TimePickerSliding(
    isShowing: Boolean = true,
    onDismiss: () -> Unit = {},
    onSelection: (hour: Int,minutes: Int) -> Unit = {_, _ -> }
) = AppTheme{
    var hour by remember { mutableStateOf(11) }
    var minutes by remember { mutableStateOf(33) }
    var amPm by remember { mutableStateOf("AM") }

    if(isShowing) Dialog(onDismissRequest = { onDismiss() }){
        Card(shape = RoundedCornerShape(8.dp)) {
            Column(Modifier.padding(vertical = 28.dp, horizontal = 4.dp)) {

                Row {
                    ListItemPicker(
                        value = hour,
                        onValueChange = { hour = it },
                        list = List(24) { index -> index },
                        textStyle = MaterialTheme.typography.h4,
                        modifier = Modifier.sizeIn(minWidth = 100.dp)
                    )
                    Box(
                        Modifier
                            .padding(vertical = 64.dp)
                            .height(80.dp)
                            .width(0.5.dp)
                            .background(color = Color(0xFF555555))
                            .align(Alignment.CenterVertically)
                    )
                    ListItemPicker(
                        value = minutes,
                        onValueChange = { minutes = it },
                        list = List(60) { index -> index },
                        textStyle = MaterialTheme.typography.h4,
                        modifier = Modifier.sizeIn(minWidth = 100.dp)
                    )
                    Box(
                        Modifier
                            .padding(vertical = 64.dp)
                            .height(80.dp)
                            .width(0.5.dp)
                            .background(color = Color(0xFF555555))
                            .align(Alignment.CenterVertically)
                    )
                    ListItemPicker(
                        value = amPm,
                        onValueChange = { amPm = it },
                        list = listOf("AM", "PM"),
                        textStyle = MaterialTheme.typography.h4,
                        modifier = Modifier.sizeIn(minWidth = 100.dp)
                    )
                }

                OutlinedButton(
                    onClick = {
                        onSelection(hour, minutes)
                        onDismiss()
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .width(100.dp),
                    content = {
                        Text(text = "Done")
                    }
                )
            }
        }
    }
}
