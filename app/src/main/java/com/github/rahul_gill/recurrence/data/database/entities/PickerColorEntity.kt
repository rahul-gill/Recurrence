package com.github.rahul_gill.recurrence.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "COLORS_TABLE")
data class PickerColorEntity(
    @PrimaryKey var pickerColor: Int,
    var dateTime: String
)