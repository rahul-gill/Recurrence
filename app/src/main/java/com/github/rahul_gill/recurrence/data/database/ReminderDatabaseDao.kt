package com.github.rahul_gill.recurrence.data.database

import androidx.room.*
import com.github.rahul_gill.recurrence.data.database.entities.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addReminder(reminder: ReminderEntity)

    @Delete
    fun deleteReminder(reminder: ReminderEntity)


    @Query("SELECT notificationId FROM REMINDERS_TABLE ORDER BY notificationId DESC LIMIT 1")
    fun getLastReminderId(): Flow<Int?>

    @Query("SELECT * FROM REMINDERS_TABLE WHERE numberShown < numberToShow OR foreverState = 1 ORDER BY dateTime")
    fun getActiveRemindersList(): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM REMINDERS_TABLE WHERE numberShown = numberToShow AND foreverState = 0 ORDER BY dateTime")
    fun getInactiveRemindersList(): Flow<List<ReminderEntity>>

    @Query("SELECT * FROM REMINDERS_TABLE WHERE notificationId = :id LIMIT 1")
    fun getReminder(id: Int): Flow<ReminderEntity>

    @Query("SELECT EXISTS(SELECT * FROM REMINDERS_TABLE WHERE notificationId = :id LIMIT 1)")
    fun isReminderPresent(id: Int): Flow<Boolean>

}