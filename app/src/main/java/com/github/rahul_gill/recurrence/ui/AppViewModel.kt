package com.github.rahul_gill.recurrence.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.rahul_gill.recurrence.data.database.ReminderDatabaseDao
import com.github.rahul_gill.recurrence.data.database.entities.ReminderEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    val dao: ReminderDatabaseDao
) : ViewModel() {

    fun addReminder(reminder: ReminderEntity) = viewModelScope.launch(Dispatchers.IO) {
        dao.addReminder(reminder)
        Log.d("added", reminder.toString())
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
                dao.getActiveRemindersList().collect { activeReminders ->
                    _activeRemindersList.value = activeReminders
                    Log.d("list", activeReminders.toString())
                }
            }
            launch {
                    dao.getLastReminderId()
                        .catch { }
                        //TODO delegate null handling to repository
                        .collect {
                            if(it != null)
                                _lastNotificationId.value = it

                            Log.d("last id", "$it")
                        }
            }
        }
    }
}