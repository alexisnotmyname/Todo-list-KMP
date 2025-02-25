package com.alexc.ph.core.presentation

expect object DateTime {
    fun getCurrentTimeInMilliSeconds(): Long
    fun getDateInMilliSeconds(timeStamp: String, inputFormat: String): Long
}