package com.github.rahul_gill.recurrence.ui.screens

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.github.rahul_gill.recurrence.BuildConfig
import com.github.rahul_gill.recurrence.R
import com.github.rahul_gill.recurrence.ui.destinations.CreateScreenDestination
import com.github.rahul_gill.recurrence.ui.theme.AppTheme
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator



@Destination
@Composable
fun AboutScreen(navigator: DestinationsNavigator) = AppTheme {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.about)) },
                navigationIcon = {
                    IconButton(onClick = { /* navigate back */ }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navigator.navigate(CreateScreenDestination) }) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = "")
            }
        },
        content = {
            Column {
                Row(
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(top = 24.dp, bottom = 32.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher),
                        contentDescription = stringResource(R.string.app_name),
                        modifier = Modifier.width(84.dp),
                        contentScale = ContentScale.FillWidth
                    )
                    Column {
                        Text(text = stringResource(R.string.app_name))
                        Text(
                            text = stringResource(R.string.rahul_gill),
                            style = MaterialTheme.typography.caption
                        )
                    }
                }
                Divider(
                    thickness = 0.5.dp,
                    color = Color.Black,
                )
                val context = LocalContext.current
                Card(
                    elevation = 0.dp,
                    shape = RectangleShape,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val intent = Intent(Intent.ACTION_SEND)
                            intent.type = "plain/text"
                            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(context.getString(R.string.contact_email)))
                            context.startActivity(Intent.createChooser(intent, context.getString(R.string.send_email)))
                        }
                ) {
                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        text = stringResource(R.string.contact)
                    )
                }
                Divider(
                    thickness = 0.5.dp,
                    color = Color.Black,
                )
                Card(
                    elevation = 0.dp,
                    shape = RectangleShape,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        }
                ) {
                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        text = stringResource(R.string.action_share)
                    )
                }
                Divider(
                    thickness = 0.5.dp,
                    color = Color.Black,
                )
                Card(
                    elevation = 0.dp,
                    shape = RectangleShape,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        }
                ) {
                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        text = stringResource(R.string.libraries)
                    )
                }
                Divider(
                    thickness = 0.5.dp,
                    color = Color.Black,
                )
                Card(
                    elevation = 0.dp,
                    shape = RectangleShape,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {

                        }
                ) {
                    Text(
                        modifier = Modifier
                            .padding(16.dp),
                        text = stringResource(R.string.thanks_to)
                    )
                }
                Divider(
                    thickness = 0.5.dp,
                    color = Color.Black,
                )
                Text(
                    text = BuildConfig.VERSION_NAME,
                    modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(top = 8.dp)
                )
            }
        }
    )
}
