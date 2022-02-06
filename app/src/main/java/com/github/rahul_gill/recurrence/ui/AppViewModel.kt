package com.github.rahul_gill.recurrence.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.rahul_gill.recurrence.data.RemindersRepository
import com.github.rahul_gill.recurrence.data.database.entities.ReminderEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    val remindersRepository: RemindersRepository
) : ViewModel() {

    fun addReminder(reminder: ReminderEntity) = viewModelScope.launch(Dispatchers.IO) {
        remindersRepository.addReminder(reminder)
    }

    private val _activeRemindersList =  MutableStateFlow<List<ReminderEntity>>(emptyList())
    private val _lastNotificationId = MutableStateFlow(1)

    val activeRemindersList
        get() = _activeRemindersList

    val lastNotificationId
        get() = _lastNotificationId

    init {
        viewModelScope.launch(Dispatchers.IO) {
            launch {
                remindersRepository.getActiveRemindersList().collect { activeReminders ->
                    _activeRemindersList.value = activeReminders
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
}