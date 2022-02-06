package com.github.rahul_gill.recurrence.data

import android.content.Context
import android.content.Intent
import com.github.rahul_gill.recurrence.data.database.ReminderDatabaseDao
import com.github.rahul_gill.recurrence.data.database.entities.ReminderEntity
import com.github.rahul_gill.recurrence.notifications.AlarmManager
import com.github.rahul_gill.recurrence.notifications.receivers.AlarmReceiver
import com.github.rahul_gill.recurrence.notifications.receivers.SnoozeReceiver
import kotlinx.coroutines.flow.map

class RemindersRepositoryImpl(val appContext: Context, val dao: ReminderDatabaseDao): RemindersRepository {
    override fun addReminder(reminder: ReminderEntity) {
        dao.addReminder(reminder)
        AlarmManager.setAlarm(
            appContext,
            Intent(appContext, AlarmReceiver::class.java),
            reminder.notificationId,
            reminder.dateTime
        )

    }

    override fun getActiveRemindersList() = dao.getActiveRemindersList()

    override fun getInactiveRemindersList() = dao.getInactiveRemindersList()

    override fun getLatestReminderId() = dao.getLastReminderId().map { id -> id ?: 1 }

    override fun deleteReminder(reminder: ReminderEntity) {
        dao.deleteReminder(reminder)
        listOf(
            Intent(appContext, AlarmReceiver::class.java),
            Intent(appContext, SnoozeReceiver::class.java)
        ).forEach{ intent ->
            AlarmManager.cancelAlarm(appContext,intent,reminder.notificationId)
        }
    }
}