package com.github.rahul_gill.recurrence.data

import com.github.rahul_gill.recurrence.data.database.entities.ReminderEntity
import kotlinx.coroutines.flow.Flow

interface RemindersRepository {
    fun addReminder(reminder: ReminderEntity)

    fun getActiveRemindersList(): Flow<List<ReminderEntity>>

    fun getInactiveRemindersList(): Flow<List<ReminderEntity>>

    fun getLatestReminderId(): Flow<Int>

    fun deleteReminder(reminder: ReminderEntity)
}