package com.fcaputo.parkingapp.utils

import com.fcaputo.parkingapp.utils.Constants.NULL_STRING
import com.fcaputo.parkingapp.utils.Constants.TIMEZONE_ID
import java.util.Calendar
import java.util.TimeZone

// STRING

fun String.hasData(): Boolean = this.isNotEmpty() && this.isNotBlank() && this != NULL_STRING

// CALENDAR

/*
* SECTION MADE TO SIMPLIFY THE INTERFACE OF CALENDAR, WHICH ONLY HAS AN 'ADD' METHOD OF UNIT TYPE
* This way I can mimic the LocalDateTime behavior, i.e. chain one method to another instead of
* overusing the 'apply' block
*/

fun Calendar.plusHours(hours: Int): Calendar {
    val newCalendar = this.clone() as Calendar
    newCalendar.add(Calendar.HOUR_OF_DAY, hours)
    return newCalendar
}

fun Calendar.minusHours(hours: Int): Calendar {
    val newCalendar = this.clone() as Calendar
    newCalendar.add(Calendar.HOUR_OF_DAY, -hours)
    return newCalendar
}

fun Calendar.plusMinutes(minutes: Int): Calendar {
    val newCalendar = this.clone() as Calendar
    newCalendar.add(Calendar.MINUTE, minutes)
    return newCalendar
}

fun Calendar.minusMinutes(minutes: Int): Calendar {
    val newCalendar = this.clone() as Calendar
    newCalendar.add(Calendar.MINUTE, -minutes)
    return newCalendar
}

fun Calendar.plusWeeks(weeks: Int): Calendar {
    val newCalendar = this.clone() as Calendar
    newCalendar.add(Calendar.WEEK_OF_YEAR, weeks)
    return newCalendar
}

fun Calendar.atTime(hour: Int, minutes: Int): Calendar {
    val newCalendar = (this.clone() as Calendar).apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minutes)
        time
        time
    }
    return newCalendar
}

fun Calendar.trimAtMinutes(): Calendar {
    val newCalendar = (this.clone() as Calendar).apply {
        set(Calendar.SECOND, 0)
        set(Calendar.MILLISECOND, 0)
        time
        time
    }
    return newCalendar
}

fun getUtcCalendarInstance(): Calendar {
    return Calendar.getInstance(TimeZone.getTimeZone(TIMEZONE_ID))
}
