package com.alexc.ph.core.presentation

import platform.Foundation.*

actual object DateTime {
    actual fun getCurrentTimeInMilliSeconds(): Long {
        return (NSDate().timeIntervalSince1970 * 1000).toLong()
    }

    actual fun getDateInMilliSeconds(timeStamp: String, inputFormat: String): Long {
        if (timeStamp.trim().isEmpty()) return getCurrentTimeInMilliSeconds()

        val df = NSDateFormatter().apply {
            dateFormat = inputFormat
        }
        val date = df.dateFromString(timeStamp)
        return (date!!.timeIntervalSince1970 * 1000).toLong()
    }
}