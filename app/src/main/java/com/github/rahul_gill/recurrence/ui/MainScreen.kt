package com.github.rahul_gill.recurrence.ui

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import com.github.rahul_gill.recurrence.ui.destinations.AboutScreenDestination
import com.github.rahul_gill.recurrence.ui.destinations.CreateScreenDestination
import com.github.rahul_gill.recurrence.ui.theme.AppTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(start = true)
@Composable
fun MainScreen(navigator: DestinationsNavigator) = AppTheme {
    var showMenu by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Title") },
                actions = {
                    IconButton(onClick = { showMenu = !showMenu }) {
                        Icon(imageVector = Icons.Default.MoreVert, contentDescription = "")
                    }
                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(onClick = { /*TODO*/ }) {
                            Text(text = "Settings")
                        }
                        DropdownMenuItem(onClick = { navigator.navigate(AboutScreenDestination) }) {
                            Text(text = "About")
                        }
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
            RemindersListScreen()
        }
    )
}