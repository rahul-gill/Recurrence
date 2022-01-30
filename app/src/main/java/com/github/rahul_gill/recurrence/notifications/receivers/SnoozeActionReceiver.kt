package com.github.rahul_gill.recurrence.notifications.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import com.github.rahul_gill.recurrence.data.preferences.PreferencesManager
import com.github.rahul_gill.recurrence.notifications.AlarmManager
import com.github.rahul_gill.recurrence.ui.MainActivity
import com.github.rahul_gill.recurrence.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint

class SnoozeActionReceiver @Inject constructor(val preferencesManager: PreferencesManager) : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val reminderId = intent.getIntExtra(Constants.NOTIFICATION_ID, 0)
        GlobalScope.launch {
            preferencesManager.checkBoxNagging
                .first()
                .takeIf { it }
                ?.let {
                    AlarmManager.cancelAlarm(context, Intent(context, NagReceiver::class.java), reminderId)
                }
        }

        // fixme what do of it
        val closeIntent = Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)
        context.sendBroadcast(closeIntent)
        val snoozeIntent = Intent(context, MainActivity::class.java)
        snoozeIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        snoozeIntent.putExtra(Constants.NOTIFICATION_ID, reminderId)
        context.startActivity(snoozeIntent)
    }
}