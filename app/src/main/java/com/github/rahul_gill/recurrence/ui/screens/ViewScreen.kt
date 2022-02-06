package com.github.rahul_gill.recurrence.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.rahul_gill.recurrence.ui.theme.AppTheme
import com.ramcosta.composedestinations.annotation.Destination

@Preview(showBackground = true)
@Destination
@Composable
fun ViewScreen() = AppTheme {
    var expanded by remember { mutableStateOf(false) }
    Box {
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopEnd)
        ) {
            DropdownMenuItem(
                onClick = {
                    /*Show share bottom sheet*/
                    expanded = false
                },
                content = { Text(text = "Share") }
            )
            DropdownMenuItem(
                onClick = {
                    /*Show notification*/
                    expanded = false
                },
                content = { Text(text = "Show Now") }
            )
        }

        Column {
            Box(
                modifier = Modifier
                    .height(150.dp)
                    .background(MaterialTheme.colors.primarySurface)
                    .fillMaxWidth()
            ) {
                IconButton(
                    onClick = { /*go back*/ },
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp),
                    content = {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "go back",
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                )
                Row(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(vertical = 8.dp)
                ) {
                    IconButton(
                        onClick = { /*edit*/ },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "go back",
                                tint = MaterialTheme.colors.onPrimary
                            )
                        }
                    )
                    IconButton(
                        onClick = { /*delete*/ },
                        content = {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "go back",
                                tint = MaterialTheme.colors.onPrimary
                            )
                        }
                    )
                    IconButton(
                        onClick = { expanded = true },
                        content = {
                            Icon(
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "go back",
                                tint = MaterialTheme.colors.onPrimary
                            )
                        }
                    )
                }

                Card(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 16.dp)
                        .background(MaterialTheme.colors.onPrimary)
                        .sizeIn(minHeight = 70.dp)
                ) {
                    Row {
                        Box(Modifier.width(8.dp))
                        Column {
                            Box(Modifier.height(8.dp))
                            Image(
                                imageVector = Icons.Default.Favorite,
                                contentDescription = "",
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(Color.Red)
                                    .padding(8.dp),
                                colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary)
                            )
                        }
                        Column(verticalArrangement = Arrangement.Top) {
                            Text(
                                text = "Title text",
                                style = MaterialTheme.typography.body1,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .padding(top = 8.dp)
                            )
                            Text(
                                text = "subtitle text",
                                style = MaterialTheme.typography.caption,
                                modifier = Modifier
                                    .padding(horizontal = 8.dp)
                                    .padding(bottom = 8.dp, top = 2.dp)
                            )
                        }
                        Box(modifier = Modifier.weight(1f))
                        Text(
                            text = "9:35 PM",
                            style = MaterialTheme.typography.caption,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                }

            }
            Row {
                Icon(
                    imageVector = Icons.Filled.Schedule,
                    contentDescription = "",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                )
                Text(
                    text = "9:35 PM",
                    modifier = Modifier.padding(horizontal = 22.dp, vertical = 16.dp)
                )
            }
            Divider(thickness = 0.5.dp, color = Color.Gray)
            Row {
                Icon(
                    imageVector = Icons.Filled.Event,
                    contentDescription = "",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                )
                Text(
                    text = "Saturday, 22 January 2022",
                    modifier = Modifier.padding(horizontal = 22.dp, vertical = 16.dp)
                )
            }
            Divider(thickness = 0.5.dp, color = Color.Gray)
            Row {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                )
                Text(
                    text = "Does not repeat",
                    modifier = Modifier.padding(horizontal = 22.dp, vertical = 16.dp)
                )
            }
            Divider(thickness = 0.5.dp, color = Color.Gray)
            Row {
                Icon(
                    imageVector = Icons.Default.Visibility,
                    contentDescription = "",
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                )
                Text(
                    text = "Shown 1 out of 1 times",
                    modifier = Modifier.padding(horizontal = 22.dp, vertical = 16.dp)
                )
            }
            Divider(thickness = 0.5.dp, color = Color.Gray)
        }
    }
}