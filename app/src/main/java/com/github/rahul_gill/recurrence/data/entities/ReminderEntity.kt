package com.github.rahul_gill.recurrence.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "REMINDERS_TABLE")
data class ReminderEntity(
    @PrimaryKey var notificationId: Int,
    var title: String,
    var content: String,
    var dateTime: String,////////////////////////////
    var repeatType: Int,
    var foreverState: Boolean,
    var numberToShow: Int,
    var numberShown: Int,
    var icon: String,
    var color: String,
    var interval: Int,
    var daysOfWeek: Int // bitset
){
    enum class ReminderState{
        ACTIVE,
        INACTIVE
    }
    enum class RepetitionType{
        DOES_NOT_REPEAT,
        HOURLY,
        DAILY,
        WEEKLY,
        MONTHLY,
        YEARLY,
        SPECIFIC_DAYS,
        ADVANCED
    }
}
