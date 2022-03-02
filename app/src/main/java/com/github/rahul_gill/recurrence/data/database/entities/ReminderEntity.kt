package com.github.rahul_gill.recurrence.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime

typealias DaysOfWeekBitset = Int

@Entity(tableName = "REMINDERS_TABLE")
data class ReminderEntity(
    @PrimaryKey
    var notificationId: Int = 1,
    var title: String,
    var content: String,
    var dateTime: LocalDateTime,
    var repeatType: RepetitionType,
    var foreverState: Boolean,
    var numberToShow: Int,
    var numberShown: Int,
    var icon: String,
    var color: Long, // must be in argb
    var interval: Long,
    var timeForDaysOfWeek: Map<DayOfWeek, LocalTime>
){
    fun occursOnDayOfWeek(day: DayOfWeek)
//        = daysOfWeek.hasDay(day)
        = timeForDaysOfWeek.containsKey(day)
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


fun DaysOfWeekBitset.fromDaysOfWeek(vararg days: DayOfWeek): DaysOfWeekBitset{
    var i = 0
    for(d in days) i = i or (1 shl d.value)
    return i
}


val DaysOfWeekBitset.daysOfWeek
    get() = run {
        val days: ArrayList<DayOfWeek> = arrayListOf()
        for(i in 1..7) if( (i shl this) and 1 == 1) days.add(DayOfWeek.of(i))
        days
    }

fun DaysOfWeekBitset.hasDay(day: DayOfWeek) = ((this shr day.value) and 1) == 1