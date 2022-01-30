package com.github.rahul_gill.recurrence.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ICONS_TABLE")
data class IconEntity(
    @PrimaryKey var id: String,
    var name: String,
    var usageFrequency: Int
)
