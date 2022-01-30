package com.github.rahul_gill.recurrence.models

data class Reminder(
    var notificationId: Int,
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
)