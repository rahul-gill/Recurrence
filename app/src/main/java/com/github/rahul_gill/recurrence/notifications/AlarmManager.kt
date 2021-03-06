package com.github.rahul_gill.recurrence.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.github.rahul_gill.recurrence.data.database.ReminderDatabaseDao
import com.github.rahul_gill.recurrence.data.database.entities.ReminderEntity
import com.github.rahul_gill.recurrence.data.database.entities.RepetitionType.*
import com.github.rahul_gill.recurrence.notifications.receivers.AlarmReceiver
import com.github.rahul_gill.recurrence.utils.Constants
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.TemporalAdjusters


object AlarmManager {
    fun setAlarm(context: Context, intent: Intent, notificationId: Int, calendar: LocalDateTime) {
        (context.getSystemService(Context.ALARM_SERVICE) as AlarmManager).let { alarmManager ->
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                (calendar.atZone(ZoneOffset.systemDefault()).toEpochSecond() * 1000),
                intent.run {
                    putExtra(Constants.NOTIFICATION_ID, notificationId)
                    PendingIntent.getBroadcast(context, notificationId, this, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
                }
            )
        }
    }

    fun cancelAlarm(context: Context, intent: Intent, notificationId: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent = PendingIntent.getBroadcast(context, notificationId, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        alarmManager.cancel(pendingIntent)
    }

    
    fun setNextAlarm(context: Context, reminder: ReminderEntity, dao: ReminderDatabaseDao) {
        val calendar = reminder.dateTime.withSecond(0)

        val newReminder = reminder.copy(dateTime = when(reminder.repeatType){
            HOURLY -> calendar.plusHours(reminder.interval)
            DAILY -> calendar.plusDays(reminder.interval)
            WEEKLY -> calendar.plusWeeks(reminder.interval)
            MONTHLY -> calendar.plusMonths(reminder.interval)
            YEARLY -> calendar.plusYears(reminder.interval)
            SPECIFIC_DAYS -> calendar.let {
                var modifierCalendar =  it.plusDays(1)
                for (i in 1..7) {
                    val dayOfWeek = java.time.DayOfWeek.of(i)
                    if (reminder.timeForDaysOfWeek.containsKey(dayOfWeek)) {
                        modifierCalendar = LocalDateTime.of(
                            modifierCalendar.toLocalDate().with(TemporalAdjusters.next(dayOfWeek)),
                            reminder.timeForDaysOfWeek[dayOfWeek]!!
                        )
                        break
                    }
                }
                modifierCalendar
            }

            ADVANCED ->  calendar //TODO()
            DOES_NOT_REPEAT ->  calendar
        })


        dao.addReminder(newReminder)
        setAlarm(context, Intent(context, AlarmReceiver::class.java), newReminder.notificationId, calendar)
    }
}