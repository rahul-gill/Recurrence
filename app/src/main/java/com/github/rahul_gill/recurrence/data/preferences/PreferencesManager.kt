package com.github.rahul_gill.recurrence.data.preferences

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.github.rahul_gill.recurrence.data.preferences.PreferencesManager.PreferencesKeys.CHECK_BOX_LED
import com.github.rahul_gill.recurrence.data.preferences.PreferencesManager.PreferencesKeys.CHECK_BOX_MARK_AS_DONE
import com.github.rahul_gill.recurrence.data.preferences.PreferencesManager.PreferencesKeys.CHECK_BOX_NAGGING
import com.github.rahul_gill.recurrence.data.preferences.PreferencesManager.PreferencesKeys.CHECK_BOX_ONGOING
import com.github.rahul_gill.recurrence.data.preferences.PreferencesManager.PreferencesKeys.CHECK_BOX_SNOOZE
import com.github.rahul_gill.recurrence.data.preferences.PreferencesManager.PreferencesKeys.CHECK_BOX_VIBRATE
import com.github.rahul_gill.recurrence.data.preferences.PreferencesManager.PreferencesKeys.IS_THEME_DARK
import com.github.rahul_gill.recurrence.data.preferences.PreferencesManager.PreferencesKeys.NAG_MINUTES
import com.github.rahul_gill.recurrence.data.preferences.PreferencesManager.PreferencesKeys.NAG_SECONDS
import com.github.rahul_gill.recurrence.data.preferences.PreferencesManager.PreferencesKeys.NOTIFICATION_SOUND
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
class PreferencesManager @Inject constructor(@ApplicationContext val context: Context) {


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
        get() = preferencesFlow.map { pref -> pref[CHECK_BOX_NAGGING] ?: false }

    val nagMinutes: Flow<Int>
        get() = preferencesFlow.map { pref -> pref[NAG_MINUTES] ?: 5 }

    val nagSeconds: Flow<Int>
        get() = preferencesFlow.map { pref -> pref[NAG_SECONDS] ?: 0 }

    val notificationSoundUri: Flow<String>
        get() = preferencesFlow.map { pref -> pref[NOTIFICATION_SOUND] ?: "" }

    val checkBoxLed: Flow<Boolean>
        get() = preferencesFlow.map { pref -> pref[CHECK_BOX_LED] ?: true }

    val checkBoxVibrate: Flow<Boolean>
        get() = preferencesFlow.map { pref -> pref[CHECK_BOX_VIBRATE] ?: false }

    val checkBoxOngoing: Flow<Boolean>
        get() = preferencesFlow.map { pref -> pref[CHECK_BOX_ONGOING] ?: false }

    val checkBoxMarkAsDone: Flow<Boolean>
        get() = preferencesFlow.map { pref -> pref[CHECK_BOX_MARK_AS_DONE] ?: false }

    val checkBoxSnooze: Flow<Boolean>
        get() = preferencesFlow.map { pref -> pref[CHECK_BOX_SNOOZE] ?: false }

    val themeIsDark: Flow<Boolean?>
        get() = preferencesFlow.map { pref -> pref[IS_THEME_DARK] }

    suspend fun setTheme(darkTheme: Boolean): Boolean {
        try {
            context.dataStore.edit {
                it[IS_THEME_DARK] = darkTheme
            }
        }
        catch(e: IOException){

            Log.e(TAG, "Error writing to preferences", e)
            return false
        }
        return true
    }

    /** nulls will be ignored(not saved) */
     suspend fun setNotificationProperties(
        checkBoxNagging: Boolean? = null,
        nagMinutes: Int? = null,
        nagSeconds: Int? = null,
        checkBoxLed: Boolean? = null,
        notificationSoundUri: String? = null,
        checkBoxVibrate: Boolean? = null,
        checkBoxOngoing: Boolean? = null,
        checkBoxMarkAsDone: Boolean? = null,
        checkBoxSnooze: Boolean? = null
     ): Boolean{
        try {
            context.dataStore.edit { prefs ->
                checkBoxNagging?.let { prefs[CHECK_BOX_NAGGING] = it}
                nagMinutes?.let { prefs[NAG_MINUTES] = it }
                nagSeconds?.let { prefs[NAG_SECONDS] = it }
                checkBoxLed?.let { prefs[CHECK_BOX_LED] = it }
                notificationSoundUri?.let { prefs[NOTIFICATION_SOUND] = it }
                checkBoxVibrate?.let { prefs[CHECK_BOX_VIBRATE] = it }
                checkBoxOngoing?.let { prefs[CHECK_BOX_ONGOING] = it }
                checkBoxMarkAsDone?.let { prefs[CHECK_BOX_MARK_AS_DONE] = it }
                checkBoxSnooze?.let { prefs[CHECK_BOX_SNOOZE] = it }
            }
        }
        catch(e: IOException){
            Log.e(TAG, "Error writing to preferences", e)
            return false
        }
        return true
     }

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
        val IS_THEME_DARK = booleanPreferencesKey("isThemeDark")
    }
}