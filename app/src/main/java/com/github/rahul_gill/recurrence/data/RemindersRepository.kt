package com.github.rahul_gill.recurrence.data

import com.github.rahul_gill.recurrence.data.database.entities.ReminderEntity

interface RemindersRepository {
    fun addReminder(reminder: ReminderEntity)

    fun getRemindersList(): List<ReminderEntity>

    fun getLatestReminderId(): Int
}