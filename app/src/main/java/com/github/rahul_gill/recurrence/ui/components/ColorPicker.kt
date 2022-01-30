package com.github.rahul_gill.recurrence.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.github.rahul_gill.recurrence.R
import com.github.rahul_gill.recurrence.ui.theme.AppTheme

private const val itemsInSingleRow = 5
private val iconsSpacing = 4.dp


@Preview
@Composable
fun ColorPicker(onColorSelected: (Long) -> Unit = {}, onDismissRequest: () -> Unit = {}) = AppTheme {
    Dialog(onDismissRequest = { onDismissRequest() }, properties = DialogProperties()) {
        Column(Modifier
            .background(
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(size = 16.dp)
            )
            .fillMaxWidth()
        ) {
            Text(
                text = LocalContext.current.getString(R.string.select_colour),
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(horizontal =  16.dp).padding(top = 8.dp, bottom = 4.dp)
            )
            LazyColumn(Modifier
                .align(Alignment.CenterHorizontally)
                .padding(start = 16.dp + iconsSpacing, end =16.dp, bottom = 16.dp)
            ){
                items(count = colors.size / 4){ index ->
                    val iconsEnd =
                        if(index + itemsInSingleRow <= colors.size) index + itemsInSingleRow
                        else colors.size
                    Row(horizontalArrangement = Arrangement.SpaceEvenly){
                        for(i in index until iconsEnd) {
                            Box(Modifier
                                .size(50.dp)
                                .padding(end = iconsSpacing, bottom = iconsSpacing)
                                .clickable { onColorSelected(colors[index+i]) }
                                .background(
                                    color = Color(colors[index+i]),
                                    shape = CircleShape
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

private val colors = arrayListOf(
    0xFFD9BCAD,
    0xFFE2F587,
    0xFFBCF593,
    0xFF8CE2FF,
    0xFF9FBCF5,
    0xFFFF99E5,
    0xFFFF8C8C,
    0xFFFF8C8C
)