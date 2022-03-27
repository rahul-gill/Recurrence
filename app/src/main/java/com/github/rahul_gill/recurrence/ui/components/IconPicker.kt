package com.github.rahul_gill.recurrence.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.github.rahul_gill.recurrence.R
import com.github.rahul_gill.recurrence.utils.IconsUtil.iconMapX


private const val itemsInSingleRow = 5
private val iconsSpacing = 4.dp

@OptIn(ExperimentalFoundationApi::class)
@Preview
@Composable
fun IconPicker(onIconSelected: (String) -> Unit = {}, onDismissRequest: () -> Unit = {}){
    Dialog(onDismissRequest = { onDismissRequest() }, properties = DialogProperties()) {
        Column(
            Modifier
                .background(
                    color = MaterialTheme.colors.surface,
                    shape = RoundedCornerShape(size = 16.dp)
                )
                .fillMaxWidth()
                .height(300.dp)
        ) {
            Text(
                text = LocalContext.current.getString(R.string.select_icon),
                style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 8.dp, bottom = 16.dp)
            )

//            val iconsList = IconsUtil.iconsMap.toList()
            val context = LocalContext.current
            LazyVerticalGrid(
                cells = GridCells.Fixed(count = 5),
                modifier = Modifier
                        .align(CenterHorizontally)
                        .padding(start = iconsSpacing, bottom = 16.dp)
                        .fillMaxWidth()
            ){
                items(iconMapX.toList()){ nameIconPair ->
                    Image(
                            painter = painterResource(id = context.resources.getIdentifier(nameIconPair.second, "drawable", context.packageName)),
                            contentDescription = "",
                            colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
                            modifier = Modifier
                                    .size(48.dp)
                                    .padding(end = iconsSpacing, bottom = iconsSpacing)
                                    .clickable {
                                        onIconSelected(nameIconPair.first)
                                        onDismissRequest()
                                    }
                    )
                }
            }
        }
    }
}
