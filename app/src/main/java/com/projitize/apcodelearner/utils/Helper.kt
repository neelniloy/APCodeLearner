package com.braineer.scheduler.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun getFormattedDateTime(millis: Long, format: String) =
    SimpleDateFormat(format, Locale.getDefault()).format(Date(millis))

fun getDateTimeInMillis(srcDate: String, dateFormat: String): Long {
    val desiredFormat = SimpleDateFormat(dateFormat)
    var dateInMillis: Long = 0
    try {
        val date = desiredFormat.parse(srcDate)
        dateInMillis = date.time
        return dateInMillis
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return 0
}