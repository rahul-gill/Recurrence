package com.github.rahul_gill.recurrence.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.rahul_gill.recurrence.data.RemindersRepository
import com.github.rahul_gill.recurrence.data.database.entities.ReminderEntity
import com.github.rahul_gill.recurrence.data.preferences.PreferencesManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class AppViewModel @Inject constructor(
    val remindersRepository: RemindersRepository,
    val preferencesManager: PreferencesManager
) : ViewModel() {

    fun addReminder(reminder: ReminderEntity) = viewModelScope.launch(Dispatchers.IO) {
        remindersRepository.addReminder(reminder.copy(notificationId = lastNotificationId.first() + 1))
    }


    private val _activeRemindersListGrouped =  MutableStateFlow<Map<LocalDate, List<ReminderEntity>>>(emptyMap())
    val activeRemindersListGrouped: StateFlow<Map<LocalDate, List<ReminderEntity>>>
        get() = _activeRemindersListGrouped

//    private val _activeRemindersList =  MutableStateFlow<List<ReminderEntity>>(emptyList())
    private val _lastNotificationId = MutableStateFlow(1)

//    val activeRemindersList: StateFlow<List<ReminderEntity>>
//        get() = _activeRemindersList


    val lastNotificationId
        get() = _lastNotificationId

    init {
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                remindersRepository.getActiveRemindersList().collect { activeReminders ->
//                    _activeRemindersList.value = activeReminders
                    _activeRemindersListGrouped.value = activeReminders.groupBy { it.dateTime.toLocalDate() }
                    Log.d("list", activeReminders.toString())
                }
            }
            launch {
                remindersRepository.getLatestReminderId()
                    .collect {
                        _lastNotificationId.value = it
                    }
            }
        }
    }

    fun setNotificationProperties(
        checkBoxNagging: Boolean? = null,
        nagMinutes: Int? = null,
        nagSeconds: Int? = null,
        checkBoxLed: Boolean? = null,
        notificationSoundUri: String? = null,
        checkBoxVibrate: Boolean? = null,
        checkBoxOngoing: Boolean? = null,
        checkBoxMarkAsDone: Boolean? = null,
        checkBoxSnooze: Boolean? = null
    ){
        viewModelScope.launch {
            preferencesManager.setNotificationProperties(
                checkBoxNagging,
                nagMinutes,
                nagSeconds,
                checkBoxLed,
                notificationSoundUri,
                checkBoxVibrate,
                checkBoxOngoing,
                checkBoxMarkAsDone,
                checkBoxSnooze
            )
        }
    }

}