package com.github.rahul_gill.recurrence.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.rahul_gill.recurrence.data.entities.*

@Database(
    entities = [ReminderEntity::class, PickerColorEntity::class, IconEntity::class, DaysOfWeekEntity::class], //EventEntity::class
    version = 1,
    exportSchema = false
)
abstract class  ReminderDatabase: RoomDatabase(){
    abstract val reminderDatabaseDao: ReminderDatabaseDao
}