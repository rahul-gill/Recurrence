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
import androidx.compose.ui.unit.dp
import com.github.rahul_gill.recurrence.ui.destinations.ViewScreenDestination
import com.github.rahul_gill.recurrence.ui.theme.AppTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun CreateScreen(
    navigator: DestinationsNavigator
) = AppTheme{
    var name by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }

    Column {
        Box(
            modifier = Modifier
                .height(150.dp)
                .background(MaterialTheme.colors.primarySurface)
        ) {

            FloatingActionButton(
                onClick = { navigator.navigate(ViewScreenDestination) },
                modifier = Modifier.align(Alignment.BottomEnd).padding(8.dp)
            ) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = "")
            }

            IconButton(
                onClick = { /*go back*/ },
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp),
                content = {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "go back",
                        tint = MaterialTheme.colors.onPrimary
                    )
                }
            )
            IconButton(
                onClick = { /*save*/ },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp),
                content = {
                    Icon(
                        imageVector = Icons.Default.Save,
                        contentDescription = "go back",
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
                label = { Text(text = "Title") },
                colors = myTextFieldColors()
            )
        }
        TextField(
            value = description,
            onValueChange = { description = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 2.dp)
                .padding(vertical = 8.dp),
            label = { Text(text = "Add Notification content") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            leadingIcon = { Icon(imageVector = Icons.Filled.Subject, contentDescription = "") }
        )
        Divider(thickness = 0.5.dp, color = Color.Gray)
        Row (
            Modifier.clickable {

            }.fillMaxWidth()
        ){
            Icon(
                imageVector = Icons.Filled.Schedule,
                contentDescription = "",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            Text(
                text = "now",
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 16.dp)
            )
        }
        Divider(thickness = 0.5.dp, color = Color.Gray)
        Row (
            Modifier.clickable {

            }.fillMaxWidth()
        ){
            Icon(
                imageVector = Icons.Filled.Event,
                contentDescription = "",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            Text(text = "Today",
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 16.dp)
            )
        }
        Divider(thickness = 0.5.dp, color = Color.Gray)
        Row (
            Modifier.clickable {

            }.fillMaxWidth()
        ){
            Icon(
                imageVector = Icons.Default.Notifications,
                contentDescription = "",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            Text(text = "Default Icon",
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 16.dp)
            )
        }
        Divider(thickness = 0.5.dp, color = Color.Gray)
        Row (
            Modifier.clickable {

            }.fillMaxWidth()
        ){
            Icon(
                imageVector = Icons.Default.Palette,
                contentDescription = "",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            Text(text = "Default Color",
                modifier = Modifier.padding(horizontal = 22.dp, vertical = 16.dp)
            )
        }
        Divider(thickness = 0.5.dp, color = Color.Gray)
        Row (
            Modifier.clickable {

            }.fillMaxWidth()
        ){
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "",
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
            )
            Text(text = "Does not repeat",
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