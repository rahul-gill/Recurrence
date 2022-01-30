package com.github.rahul_gill.recurrence.data.preferences

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

private const val TAG = "PreferencesManager"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "preferences")


@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext context: Context) {


    private val preferencesFlow = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }

    val checkBoxNagging: Flow<Boolean>
        get() = preferencesFlow.map { pref -> pref[PreferencesKeys.CHECK_BOX_NAGGING] ?: false }

    val nagMinutes: Flow<Int>
        get() = preferencesFlow.map { pref -> pref[PreferencesKeys.NAG_MINUTES] ?: 5 }

    val nagSeconds: Flow<Int>
        get() = preferencesFlow.map { pref -> pref[PreferencesKeys.NAG_SECONDS] ?: 0 }

    val notificationSoundUri: Flow<String>
        get() = preferencesFlow.map { pref -> pref[PreferencesKeys.NOTIFICATION_SOUND] ?: "content://settings/system/notification_sound" }

    val checkBoxLed: Flow<Boolean>
        get() = preferencesFlow.map { pref -> pref[PreferencesKeys.CHECK_BOX_LED] ?: true }

    val checkBoxVibrate: Flow<Boolean>
        get() = preferencesFlow.map { pref -> pref[PreferencesKeys.CHECK_BOX_VIBRATE] ?: false }

    val checkBoxOngoing: Flow<Boolean>
        get() = preferencesFlow.map { pref -> pref[PreferencesKeys.CHECK_BOX_ONGOING] ?: true }

    val checkBoxMarkAsDone: Flow<Boolean>
        get() = preferencesFlow.map { pref -> pref[PreferencesKeys.CHECK_BOX_MARK_AS_DONE] ?: false }

    val checkBoxSnooze: Flow<Boolean>
        get() = preferencesFlow.map { pref -> pref[PreferencesKeys.CHECK_BOX_SNOOZE] ?: false }

    private object PreferencesKeys {
        val CHECK_BOX_NAGGING = booleanPreferencesKey("checkBoxNagging")
        val NAG_MINUTES = intPreferencesKey("nagMinutes")
        val NAG_SECONDS = intPreferencesKey("nagSeconds")
        val NOTIFICATION_SOUND = stringPreferencesKey("NotificationSound")
        val CHECK_BOX_LED = booleanPreferencesKey("checkBoxLED")
        val CHECK_BOX_ONGOING = booleanPreferencesKey("checkBoxOngoing")
        val CHECK_BOX_VIBRATE = booleanPreferencesKey("checkBoxVibrate")
        val CHECK_BOX_MARK_AS_DONE = booleanPreferencesKey("checkBoxMarkAsDone")
        val CHECK_BOX_SNOOZE = booleanPreferencesKey("checkBoxSnooze")
    }
}