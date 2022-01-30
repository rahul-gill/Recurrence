package com.github.rahul_gill.recurrence.notifications.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
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

class AlarmReceiver  @Inject constructor(
    val dao: ReminderDatabaseDao,
    val notificationManager: NotificationManager
) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        GlobalScope.launch {
            dao.getReminder(intent.getIntExtra(Constants.NOTIFICATION_ID, 0)).first().let { reminder ->
                reminder.numberShown += 1
                dao.addReminder(reminder)

                notificationManager.createNotification(context, reminder)

                if (reminder.numberToShow > reminder.numberShown || reminder.foreverState)
                    AlarmManager.setNextAlarm(context, reminder, dao)

                val updateIntent = Intent(Constants.BROADCAST_REFRESH)
                LocalBroadcastManager.getInstance(context).sendBroadcast(updateIntent)
          }
        }
    }
}