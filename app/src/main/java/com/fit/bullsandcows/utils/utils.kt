package com.fit.bullsandcows.utils

import java.text.SimpleDateFormat
import java.util.*

fun Long.toTimeFormat(): String {
    return if (this > 60000)
        SimpleDateFormat("mm:ss:SS", Locale.getDefault()).format(Date(this)).toString()
    else
        SimpleDateFormat("ss:SS", Locale.getDefault()).format(Date(this)).toString()
}

fun Long.toDateFormat():String {
    return SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(this)).toString()
}