package com.github.rahul_gill.recurrence.data.database

import androidx.compose.ui.graphics.Color
import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.github.rahul_gill.recurrence.data.database.entities.TimeForDaysOfWeek
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.time.LocalDateTime

@ProvidedTypeConverter
class TypeConverters {
    @TypeConverter
    fun fromString(value: String): LocalDateTime {
        return LocalDateTime.parse(value)
    }

    @TypeConverter
    fun toString(date: LocalDateTime): String {
        return date.toString()
    }

    @TypeConverter
    fun timeForDaysOfWeekFromString(value: String): TimeForDaysOfWeek
        = Json.decodeFromString(value)

    @TypeConverter
    fun timeForDaysOfWeekTOString(value: TimeForDaysOfWeek): String
        = Json.encodeToString(value)


    @TypeConverter
    fun colorFromULong(value: ULong): Color = Color(value)

    @TypeConverter
    fun colorToSULong(value: Color): ULong = value.value

}