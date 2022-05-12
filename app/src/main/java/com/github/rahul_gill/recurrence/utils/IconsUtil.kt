package com.github.rahul_gill.recurrence.utils

import android.content.Context


object IconsUtil {
    val iconMapX = mapOf(
        "Add Alert" to "ic_baseline_add_alert_24",
        "Snooze" to "ic_baseline_snooze_24",
        "Account Balance" to "ic_baseline_account_balance_24"
    )

    fun Context.getDrawableByName(name: String)
        = resources.getIdentifier(name, "drawable", packageName)
}