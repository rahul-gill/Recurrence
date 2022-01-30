package com.github.rahul_gill.recurrence.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

typealias DaysOfWeekBitset = Int

@Entity(tableName = "REMINDERS_TABLE")
data class ReminderEntity(
    @PrimaryKey
    var notificationId: Int,
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
    var daysOfWeek: DaysOfWeekBitset
){
    fun occursOnDayOfWeek(day: java.time.DayOfWeek) = daysOfWeek.hasDay(day)


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


fun DaysOfWeekBitset.fromDaysOfWeek(vararg days: java.time.DayOfWeek): DaysOfWeekBitset{
    var i = 0
    for(d in days) i = i or (1 shl d.value)
    return i
}


val DaysOfWeekBitset.daysOfWeek
    get() = run {
        val days: ArrayList<java.time.DayOfWeek> = arrayListOf()
        for(i in 1..7) if( (i shl this) and 1 == 1) days.add(java.time.DayOfWeek.of(i))
        days
    }

fun DaysOfWeekBitset.hasDay(day: java.time.DayOfWeek) = ((this shr day.value) and 1) == 1


//foreignKeys = [
//ForeignKey(
//entity = PickerColorEntity::class,
//parentColumns = arrayOf("pickerColor"),
//childColumns = arrayOf("color"),
//onDelete = ForeignKey.CASCADE,
//onUpdate = ForeignKey.CASCADE
//),
//ForeignKey(
//entity = IconEntity::class,
//parentColumns = arrayOf("id"),
//childColumns = arrayOf("icon"),
//onDelete = ForeignKey.CASCADE,
//onUpdate = ForeignKey.CASCADE
//)
//]