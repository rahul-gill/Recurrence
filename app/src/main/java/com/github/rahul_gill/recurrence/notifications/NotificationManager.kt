package com.github.rahul_gill.recurrence.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.github.rahul_gill.recurrence.R
import com.github.rahul_gill.recurrence.data.database.entities.ReminderEntity
import com.github.rahul_gill.recurrence.data.preferences.PreferencesManager
import com.github.rahul_gill.recurrence.notifications.receivers.DismissReceiver
import com.github.rahul_gill.recurrence.notifications.receivers.NagReceiver
import com.github.rahul_gill.recurrence.notifications.receivers.SnoozeActionReceiver
import com.github.rahul_gill.recurrence.ui.MainActivity
import com.github.rahul_gill.recurrence.utils.Constants
import com.github.rahul_gill.recurrence.utils.IconsUtil
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NotificationManager @Inject constructor(val preferencesManager: PreferencesManager){
    suspend fun createNotification(context: Context, reminder: ReminderEntity) {
        // Create intent for notification onClick behaviour
        val pending = Intent(context, MainActivity::class.java).run {
            putExtra(Constants.NOTIFICATION_ID, reminder.notificationId)
            putExtra(Constants.NOTIFICATION_DISMISS, true)
            PendingIntent.getActivity(context, reminder.notificationId, this, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        }

        // Create intent for notification snooze click behaviour
        val pendingSnooze = Intent(context, SnoozeActionReceiver::class.java).run {
            putExtra(Constants.NOTIFICATION_ID, reminder.notificationId)
            PendingIntent.getBroadcast(context, reminder.notificationId, this, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
        }
        val imageResId = IconsUtil.iconsMap[reminder.icon]!!//fixme
        val builder = NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_baseline_snooze_24)
            .setColor(Color.GREEN) //fixme what is happening
            .setStyle(NotificationCompat.BigTextStyle().bigText(reminder.content))
            .setContentTitle(reminder.title)
            .setContentText(reminder.content)
            .setTicker(reminder.title)
            .setContentIntent(pending)

        if(preferencesManager.checkBoxNagging.first()){
            val pendingDismiss = Intent(context, DismissReceiver::class.java).run {
                putExtra(Constants.NOTIFICATION_ID, reminder.notificationId)
                PendingIntent.getBroadcast(context, reminder.notificationId, this, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
            }
            builder.setDeleteIntent(pendingDismiss)

            preferencesManager.nagMinutes.combine(preferencesManager.nagSeconds){ minutes, seconds ->
                    LocalDateTime.now().plusSeconds(seconds.toLong()).plusMinutes(minutes.toLong())
            }.first().let { calendar ->
                AlarmManager.setAlarm(
                    context,
                    Intent(context, NagReceiver::class.java),
                    reminder.notificationId,
                    calendar)
            }
        }

        preferencesManager.notificationSoundUri.first().let { soundUri ->
            builder.setSound(Uri.parse(soundUri))
        }

        preferencesManager.checkBoxLed.first().let { if(it)
            builder.setLights(Color.BLUE, 700, 1500)
        }

        preferencesManager.checkBoxOngoing.first().let { if(it)
            builder.setOngoing(true)
        }

        preferencesManager.checkBoxVibrate.first().let { if(it)
            builder.setVibrate(longArrayOf(0, 300, 0))
        }

        preferencesManager.checkBoxMarkAsDone.first().let { if(it)
            builder.addAction(
                R.drawable.ic_baseline_done_24,
                context.getString(R.string.mark_as_done),
                Intent(context, DismissReceiver::class.java).run {
                    putExtra(Constants.NOTIFICATION_ID, reminder.notificationId)
                    PendingIntent.getBroadcast(context, reminder.notificationId, this,PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT)
                }
            )
        }
        preferencesManager.checkBoxSnooze.first().let { if(it)
            builder.addAction(R.drawable.ic_baseline_snooze_24, context.getString(R.string.snooze), pendingSnooze)
        }


        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_ID,
                "Reminder",//TODO
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }


        Log.d("DEBUG", "notification dispatched $builder")
        notificationManager.notify(reminder.notificationId, builder.build())
    }

    fun cancelNotification(context: Context, notificationId: Int) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(notificationId)
    }
}