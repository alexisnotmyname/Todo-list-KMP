package com.alexc.ph.core.presentation

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Locale
import java.util.TimeZone

actual object DateTime {
    actual fun getCurrentTimeInMilliSeconds(): Long {
        val today = LocalDate.now()
        return today.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
    }

    actual fun getDateInMilliSeconds(
        timeStamp: String,
        inputFormat: String
    ): Long {
        if (timeStamp.trim().isEmpty()) return getCurrentTimeInMilliSeconds()

        val parser = SimpleDateFormat(inputFormat, Locale.getDefault())
        parser.timeZone = TimeZone.getTimeZone("GMT")
        return parser.parse(timeStamp)?.time ?: 0
    }
}