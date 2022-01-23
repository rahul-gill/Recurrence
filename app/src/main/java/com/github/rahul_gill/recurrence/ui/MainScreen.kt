package com.github.rahul_gill.recurrence.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.rahul_gill.recurrence.ui.theme.AppTheme

@Preview
@Composable
fun MainScreen() = AppTheme {
    var selectedTab by remember { mutableStateOf(0) }
    var expanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Row(horizontalArrangement = Arrangement.End) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "",
                )
            }
         },
        content = {
            Box(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()) {
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier =  Modifier.align(Alignment.TopEnd)
                ) {
                    DropdownMenuItem(
                        onClick = {
                            /*Show share bottom sheet*/
                            expanded = false
                        },
                        content =  { Text(text = "Settings") }
                    )
                    DropdownMenuItem(
                        onClick = {
                            /*Show notification*/
                            expanded = false
                        },
                        content =  { Text(text = "About") }
                    )
                }
//                when (selectedTab) {
//                    0 -> CreateScreen()
//                    1 -> ViewScreen()
//                    2 -> AboutScreen()
//                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { selectedTab = if(selectedTab == 2) 0 else selectedTab + 1 }) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = "")
            }
        }
    )
}