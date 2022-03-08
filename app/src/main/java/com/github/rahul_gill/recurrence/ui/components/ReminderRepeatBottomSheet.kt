package com.github.rahul_gill.recurrence.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.rahul_gill.recurrence.data.database.entities.RepetitionType
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator
import com.ramcosta.composedestinations.spec.DestinationStyle

@Destination(style = DestinationStyle.BottomSheet::class)
@Composable
fun ReminderRepeatBottomSheet(
    resultNavigator: ResultBackNavigator<RepetitionType>
) {
    val repeatTypeNames = mapOf(
        RepetitionType.DOES_NOT_REPEAT to "Does not repeat",
        RepetitionType.HOURLY to "Every Hour",
        RepetitionType.DAILY to "Every Day",
        RepetitionType.WEEKLY to "Every Week",
        RepetitionType.MONTHLY to "Every Month",
        RepetitionType.YEARLY to "Every Year",
        RepetitionType.SPECIFIC_DAYS to "Specific Weekdays",
        RepetitionType.ADVANCED to "Advanced"
    )


    Column {
        for(repeatType in repeatTypeNames){
            Card(
                elevation = 0.dp,
                modifier = Modifier
                    .clickable { resultNavigator.navigateBack(result = repeatType.key) }
                    .fillMaxWidth()
                    .padding(bottom = 2.dp)
            ) {
                Text(
                    text = repeatType.value,
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.body1
                )
            }
        }
    }
}