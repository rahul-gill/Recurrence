package com.github.rahul_gill.recurrence.data

import androidx.room.*
import com.github.rahul_gill.recurrence.data.entities.DaysOfWeekEntity
import com.github.rahul_gill.recurrence.data.entities.IconEntity
import com.github.rahul_gill.recurrence.data.entities.PickerColorEntity
import com.github.rahul_gill.recurrence.data.entities.ReminderEntity

@Dao
interface ReminderDatabaseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addReminder(reminder: ReminderEntity)

    @Query("SELECT notificationId FROM REMINDERS_TABLE ORDER BY notificationId DESC LIMIT 1")
    fun getLastReminderId(): Int

    @Query("SELECT * FROM REMINDERS_TABLE WHERE numberShown < numberToShow OR foreverState = 1 ORDER BY dateTime")
    fun getActiveRemindersList(): List<ReminderEntity>

    @Query("SELECT * FROM REMINDERS_TABLE WHERE numberShown = numberToShow AND foreverState = 0 ORDER BY dateTime")
    fun getInactiveRemindersList(): List<ReminderEntity>

    @Query("SELECT * FROM REMINDERS_TABLE WHERE notificationId = :id LIMIT 1")
    fun getReminder(id: Int): List<ReminderEntity>

    @Delete
    fun deleteReminder(reminder: ReminderEntity)

    @Query("SELECT EXISTS(SELECT * FROM REMINDERS_TABLE WHERE notificationId = :id LIMIT 1)")
    fun isReminderPresent(id: Int): Boolean

    @Query("SELECT * FROM DAYS_OF_WEEK_TABLE WHERE id = :reminderId LIMIT 1")
    fun getDaysOfWeek(reminderId: Int) : DaysOfWeekEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addDaysOfWeek(reminderDaysOfWeek: DaysOfWeekEntity)

    @Query("SELECT * FROM ICONS_TABLE ORDER BY usageFrequency DESC")
    fun getIconList(): List<IconEntity>

    @Update
    fun updateIcon(icon: IconEntity)

    @Query("SELECT pickerColor FROM COLORS_TABLE ORDER BY dateTime DESC LIMIT 14")
    fun getColors(): List<Int>

    @Insert
    fun addColor(color: PickerColorEntity)


    @Insert
    fun addAllColors(vararg  color: PickerColorEntity)

    @Insert
    fun addAllIcons(vararg icon: IconEntity)
}