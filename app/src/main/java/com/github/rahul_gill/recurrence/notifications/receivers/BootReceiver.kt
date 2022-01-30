package com.github.rahul_gill.recurrence.notifications.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import com.github.rahul_gill.recurrence.data.database.ReminderDatabaseDao
import com.github.rahul_gill.recurrence.notifications.AlarmManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint

class BootReceiver @Inject constructor(
    private val dao: ReminderDatabaseDao
)  : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        GlobalScope.launch {
            dao.getActiveRemindersList().collect {  reminders ->
                val alarmIntent = Intent(context, AlarmReceiver::class.java)
                for (reminder in reminders)
                    AlarmManager.setAlarm(context, alarmIntent, reminder.notificationId, reminder.dateTime.withSecond(0))
            }
        }
    }
}