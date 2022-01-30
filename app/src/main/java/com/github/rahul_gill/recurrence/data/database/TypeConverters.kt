package com.github.rahul_gill.recurrence.data.database

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
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

}