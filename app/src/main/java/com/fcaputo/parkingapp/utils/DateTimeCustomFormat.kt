package com.fcaputo.parkingapp.utils

import com.fcaputo.parkingapp.utils.Constants.TIMEZONE_ID
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

object DateTimeCustomFormat {
    private const val DATE_PATTERN = "dd/MM/yyyy"
    private const val TIME_PATTERN = "hh:mm aaa"
    private const val FULL_PATTERN = "$DATE_PATTERN $TIME_PATTERN"

    private val formatter: SimpleDateFormat = DateFormat.getDateTimeInstance().apply {
        timeZone = TimeZone.getTimeZone(TIMEZONE_ID)
    } as SimpleDateFormat

    private fun getFormatter(pattern: String) = formatter.apply { applyPattern(pattern) }

    fun formatUtcDate(dateInMillis: Long): String {
        val calendar = getUtcCalendarInstance().apply { timeInMillis = dateInMillis }
        return getFormatter(this.DATE_PATTERN).format(calendar.time)
    }

    fun parseUtcDate(givenDate: String): Calendar {
        return getUtcCalendarInstance().apply {
            time = getFormatter(DATE_PATTERN).parse(givenDate) as Date
            trimAtMinutes()
        }
    }

    fun formatUtcTime(hour: Int, minute: Int): String {
        val calendar = getUtcCalendarInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
        }
        return getFormatter(TIME_PATTERN).format(calendar.time)
    }

    fun parseUtcTime(givenTime: String): Calendar {
        return getUtcCalendarInstance().apply {
            time = getFormatter(TIME_PATTERN).parse(givenTime) as Date
        }
    }

    fun parseLocalDateTime(givenDateTime: String): Calendar {
        val format = DateFormat.getDateTimeInstance() as SimpleDateFormat
        format.applyPattern(FULL_PATTERN)

        return Calendar.getInstance().apply {
            time = format.parse(givenDateTime) as Date
        }
    }
}
