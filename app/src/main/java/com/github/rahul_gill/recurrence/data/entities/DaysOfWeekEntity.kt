package com.github.rahul_gill.recurrence.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "DAYS_OF_WEEK_TABLE")
data class DaysOfWeekEntity(
    @PrimaryKey var id: Int,
    var sunday: Boolean,
    var monday: Boolean,
    var tuesday: Boolean,
    var thursday: Boolean,
    var friday: Boolean,
    var saturday: Boolean,
)
