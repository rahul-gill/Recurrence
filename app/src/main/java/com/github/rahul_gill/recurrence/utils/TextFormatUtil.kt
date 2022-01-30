package com.github.rahul_gill.recurrence.utils

import android.content.Context
import com.github.rahul_gill.recurrence.R
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters

object TextFormatUtil {
    private val SHORT_WEEK_DAYS_FORMAT = DateTimeFormatter.ofPattern("E")

    private fun getShortWeekDays(): Array<String> {
        var cl = LocalDateTime.now().with(TemporalAdjusters.next(DayOfWeek.SUNDAY))
        val weekDays = Array(7) { "" }
        for (i in 0..6) {
            weekDays[i] = cl.format(SHORT_WEEK_DAYS_FORMAT)
            cl = cl.plusDays(1)
        }
        return weekDays
    }

    fun formatDaysOfWeekText(context: Context, daysOfWeek: BooleanArray): String {
        val shortWeekDays: Array<String> = getShortWeekDays()
        val stringBuilder = StringBuilder()
        stringBuilder.append(context.getString(R.string.repeats_on))
        stringBuilder.append(" ")
        for (i in daysOfWeek.indices) {
            if (daysOfWeek[i]) {
                stringBuilder.append(shortWeekDays[i])
                stringBuilder.append(" ")
            }
        }
        return stringBuilder.toString()
    }

}