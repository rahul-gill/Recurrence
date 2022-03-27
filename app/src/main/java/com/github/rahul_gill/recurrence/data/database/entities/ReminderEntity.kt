package com.github.rahul_gill.recurrence.data.database.entities

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

@Entity(tableName = "REMINDERS_TABLE")
data class ReminderEntity @JvmOverloads constructor(
    @PrimaryKey
    val notificationId: Int = 1,
    val title: String,
    val content: String,
    val dateTime: LocalDateTime,
    val repeatType: RepetitionType,
    val foreverState: Boolean,
    val numberToShow: Int,
    val numberShown: Int,
    val icon: String,
    val color: Int, // must be in argb
    val interval: Long,
    val timeForDaysOfWeek: TimeForDaysOfWeek
)

@Serializable
@Parcelize
class TimeForDaysOfWeek: HashMap<DayOfWeek, LocalTime>(), Parcelable

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