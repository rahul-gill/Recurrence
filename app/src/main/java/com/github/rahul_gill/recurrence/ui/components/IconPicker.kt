package com.github.rahul_gill.recurrence.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.github.rahul_gill.recurrence.R
import com.github.rahul_gill.recurrence.utils.IconsUtil


private const val itemsInSingleRow = 5
private val iconsSpacing = 16.dp

@Preview
@Composable
fun IconPicker(onIconSelected: (String) -> Unit = {}, onDismissRequest: () -> Unit = {}){
    Dialog(onDismissRequest = { onDismissRequest() }, properties = DialogProperties()) {
        Column(Modifier
            .background(
                color = MaterialTheme.colors.surface,
                shape = RoundedCornerShape(size = 16.dp)
            )
            .fillMaxSize(fraction = 1f)
        ) {
            Text(
                text = LocalContext.current.getString(R.string.select_icon),
                style = MaterialTheme.typography.h5,
                modifier = Modifier.padding(horizontal =  16.dp).padding(top = 8.dp, bottom = 32.dp)
            )

            val iconsList = IconsUtil.iconsMap.toList()
            LazyColumn(Modifier
                .align(CenterHorizontally)
                .padding(start = 15.dp + iconsSpacing,end = 16.dp, bottom = 16.dp)
            ){
                items(iconsList.size / itemsInSingleRow){ columnNumber ->
                    val startIndex = columnNumber * itemsInSingleRow
                    val iconsEnd =
                        if(columnNumber + startIndex <= iconsList.size) startIndex + itemsInSingleRow
                        else iconsList.size
                    Row(horizontalArrangement = Arrangement.SpaceEvenly) {
                        for(i in startIndex until iconsEnd) {
                            Image(
                                imageVector =iconsList[i].second,
                                contentDescription = "",
                                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
                                modifier = Modifier
                                    .padding(end = iconsSpacing, bottom = iconsSpacing)
                                    .clickable {
                                        onIconSelected(iconsList[i].first)
                                        onDismissRequest()
                                    }
                            )
                        }
                    }
                }
            }
        }
    }
}
