package com.alexc.ph.core.presentation

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

fun getCurrentDateMillis(): Long {
    val nowInstant = Clock.System.now()
    val today: LocalDate = nowInstant.toLocalDateTime(TimeZone.currentSystemDefault()).date
    return today.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
}

fun Long.millisToDateFormat(format: String = "E, MMM dd"): String {
    // Convert millis to Instant
    val instant = Instant.fromEpochMilliseconds(this)

    // Convert Instant to LocalDateTime using system's timezone
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val dayOfWeek = localDateTime.date.dayOfWeek
    val month = localDateTime.date.month
    val dayOfMonth = localDateTime.date.dayOfMonth

    return "${dayOfWeek.name.take(3)}, ${month.name.take(3)} $dayOfMonth"
}