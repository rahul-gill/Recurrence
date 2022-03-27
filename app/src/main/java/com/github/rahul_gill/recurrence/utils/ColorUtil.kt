package com.github.rahul_gill.recurrence.utils

import androidx.core.graphics.alpha
import androidx.core.graphics.blue
import androidx.core.graphics.green
import androidx.core.graphics.red

fun Int.argbToColorInt()= android.graphics.Color.argb(
    this.alpha,
    this.red,
    this.green,
    this.blue
)