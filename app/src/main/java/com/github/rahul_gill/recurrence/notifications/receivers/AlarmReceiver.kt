package com.github.rahul_gill.recurrence.notifications.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.github.rahul_gill.recurrence.data.database.ReminderDatabaseDao
import com.github.rahul_gill.recurrence.notifications.AlarmManager
import com.github.rahul_gill.recurrence.notifications.NotificationManager
import com.github.rahul_gill.recurrence.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class AlarmReceiver : BroadcastReceiver() {

    @Inject lateinit var dao: ReminderDatabaseDao
    @Inject lateinit var notificationManager: NotificationManager

    override fun onReceive(context: Context, intent: Intent) {
        Log.d("DEBUG", "received intent $intent")
        GlobalScope.launch {
            dao.getReminder(intent.getIntExtra(Constants.NOTIFICATION_ID, 0)).first().let { prev ->
                val reminder = prev.copy(numberShown = prev.numberShown + 1)
                dao.addReminder(reminder)

                Log.d("DEBUG", "sending notification $reminder")
                notificationManager.createNotification(context, reminder)

                if (reminder.numberToShow > reminder.numberShown || reminder.foreverState)
                    AlarmManager.setNextAlarm(context, reminder, dao)

                val updateIntent = Intent(Constants.BROADCAST_REFRESH)
                LocalBroadcastManager.getInstance(context).sendBroadcast(updateIntent)
          }
        }
    }
}