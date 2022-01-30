package com.github.rahul_gill.recurrence.notifications.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import com.github.rahul_gill.recurrence.data.database.ReminderDatabaseDao
import com.github.rahul_gill.recurrence.notifications.NotificationManager
import com.github.rahul_gill.recurrence.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint

class SnoozeReceiver @Inject constructor(
    val dao: ReminderDatabaseDao,
    val notificationManager: NotificationManager
) : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val reminderId = intent.getIntExtra(Constants.NOTIFICATION_ID, 0)
        GlobalScope.launch {
            dao.isReminderPresent(reminderId)
                .first()
                .takeIf { it && reminderId != 0 }
                ?.let {
                    dao.getReminder(reminderId)
                        .first()
                        .let {
                            notificationManager.createNotification(context, it)
                        }
                }

        }
    }
}