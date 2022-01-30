package com.github.rahul_gill.recurrence.notifications.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import com.github.rahul_gill.recurrence.data.preferences.PreferencesManager
import com.github.rahul_gill.recurrence.notifications.AlarmManager
import com.github.rahul_gill.recurrence.notifications.NotificationManager
import com.github.rahul_gill.recurrence.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint

class DismissReceiver @Inject constructor(
    val notificationManager: NotificationManager,
    val preferencesManager: PreferencesManager
): BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val reminderId = intent.getIntExtra(Constants.NOTIFICATION_ID, 0)
        notificationManager.cancelNotification(context, reminderId)

        GlobalScope.launch {
            preferencesManager.checkBoxNagging.first().let { if(it)
                AlarmManager.cancelAlarm(context, Intent(context, NagReceiver::class.java), reminderId)
            }
        }

    }
}
