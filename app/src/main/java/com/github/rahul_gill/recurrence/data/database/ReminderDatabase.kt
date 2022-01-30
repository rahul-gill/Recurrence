package com.github.rahul_gill.recurrence.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.rahul_gill.recurrence.data.database.entities.DaysOfWeekEntity
import com.github.rahul_gill.recurrence.data.database.entities.IconEntity
import com.github.rahul_gill.recurrence.data.database.entities.PickerColorEntity
import com.github.rahul_gill.recurrence.data.database.entities.ReminderEntity

@Database(
    entities = [ReminderEntity::class, PickerColorEntity::class, IconEntity::class, DaysOfWeekEntity::class], //EventEntity::class
    version = 1,
    exportSchema = false
)
@TypeConverters(com.github.rahul_gill.recurrence.data.database.TypeConverters::class)
abstract class  ReminderDatabase: RoomDatabase(){
    abstract val reminderDatabaseDao: ReminderDatabaseDao
}