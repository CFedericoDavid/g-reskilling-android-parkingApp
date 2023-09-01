package com.fcaputo.parkingapp

import com.fcaputo.parkingapp.utils.DateTimeCustomFormat
import com.fcaputo.parkingapp.utils.getUtcCalendarInstance
import com.fcaputo.parkingapp.utils.trimAtMinutes
import org.junit.Assert
import org.junit.Test
import java.util.Calendar

class DateTimeCustomFormatTest {
    @Test
    fun `when a valid date string is passed, the formatter parses it correctly`() {
        val parsedDate = DateTimeCustomFormat.parseUtcDate(VALID_DATE_STRING)
        Assert.assertEquals(VALID_DATE[Calendar.YEAR], parsedDate[Calendar.YEAR])
        Assert.assertEquals(VALID_DATE[Calendar.MONTH], parsedDate[Calendar.MONTH])
        Assert.assertEquals(VALID_DATE[Calendar.DAY_OF_MONTH], parsedDate[Calendar.DAY_OF_MONTH])
    }

    @Test
    fun `when a valid date object is passed, the formatter produces the correct string`() {
        val dateString = DateTimeCustomFormat.formatUtcDate(VALID_DATE.timeInMillis)
        Assert.assertEquals(VALID_DATE_STRING, dateString)
    }

    @Test
    fun `when a valid time string is passed, it is correctly parsed`() {
        val parsedTime = DateTimeCustomFormat.parseUtcTime(VALID_TIME_STRING)
        Assert.assertEquals(VALID_TIME[Calendar.HOUR_OF_DAY], parsedTime[Calendar.HOUR_OF_DAY])
        Assert.assertEquals(VALID_TIME[Calendar.MINUTE], parsedTime[Calendar.MINUTE])
    }

    @Test
    fun `when a valid time object is passed, the formatter produces the correct string`() {
        val timeString = DateTimeCustomFormat.formatUtcTime(VALID_TIME[Calendar.HOUR_OF_DAY], VALID_TIME[Calendar.MINUTE])
        Assert.assertEquals(VALID_TIME_STRING, timeString)
    }

    @Test
    fun `when a valid datetime string is passed, it is correctly parsed`() {
        val parsedDateTime = DateTimeCustomFormat.parseLocalDateTime(VALID_DATE_TIME_STRING)
        Assert.assertEquals(0, VALID_DATE_TIME.compareTo(parsedDateTime))
    }

    companion object {
        private const val VALID_DATE_STRING = "25/09/2023"
        private val VALID_DATE: Calendar = getUtcCalendarInstance().apply {
            set(Calendar.YEAR, 2023)
            set(Calendar.MONTH, 8) //Calendar uses zero-based months
            set(Calendar.DAY_OF_MONTH, 25)
            trimAtMinutes()
        }
        private const val VALID_TIME_STRING = "09:45 PM"
        private val VALID_TIME: Calendar = getUtcCalendarInstance().apply {
            set(Calendar.HOUR_OF_DAY, 21)
            set(Calendar.MINUTE, 45)
        }

        private const val VALID_DATE_TIME_STRING = "$VALID_DATE_STRING $VALID_TIME_STRING"
        private val VALID_DATE_TIME: Calendar = Calendar.getInstance().apply {
            clear()
            set(Calendar.YEAR, 2023)
            set(Calendar.MONTH, 8) //Calendar uses zero-based months
            set(Calendar.DAY_OF_MONTH, 25)
            set(Calendar.HOUR_OF_DAY, 21)
            set(Calendar.MINUTE, 45)
        }
    }
}
