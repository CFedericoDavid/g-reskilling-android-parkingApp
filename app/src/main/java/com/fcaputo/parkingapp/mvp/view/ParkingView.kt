package com.fcaputo.parkingapp.mvp.view

import android.app.Activity
import android.text.format.Time
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.fcaputo.parkingapp.databinding.ActivityMainBinding
import com.fcaputo.parkingapp.entity.Reservation
import com.fcaputo.parkingapp.mvp.contract.ParkingContract
import com.fcaputo.parkingapp.mvp.view.base.ActivityView
import com.fcaputo.parkingapp.utils.Formatter
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneOffset
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class ParkingView(activity: Activity) : ActivityView(activity), ParkingContract.View {

    private var binding: ActivityMainBinding = ActivityMainBinding.inflate(activity.layoutInflater)

    init {
        activity.setContentView(binding.root)
    }

    override fun onSaveButtonPressed(onClick: () -> Unit) {
        binding.submitButton.setOnClickListener{ onClick() }
    }

    override fun getStartDateTime(): LocalDateTime {
        TODO("Not yet implemented")
    }

    override fun getEndDateTime(): LocalDateTime {
        TODO("Not yet implemented")
    }

    override fun showDateTimeError() {
        TODO("Not yet implemented")
    }

    override fun getParkingLotNumber(): Int {
        TODO("Not yet implemented")
    }

    override fun getSecurityCode(): Int {
        TODO("Not yet implemented")
    }

    override fun showAvailabilityError() {
        TODO("Not yet implemented")
    }

    override fun showSuccessMessage(reservationInfo: Reservation) {
        TODO("Not yet implemented")
    }

    override fun setDatePicker(textInput: EditText?, tag: String) {
        textInput?.setOnClickListener {
            val calendarConstraint = CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now())
                .build()

            val dateP = MaterialDatePicker.Builder.datePicker()
                .setCalendarConstraints(calendarConstraint)
                .build()

            dateP.show((activity as AppCompatActivity).supportFragmentManager, tag)
            dateP.addOnPositiveButtonClickListener {
                //The date stored in "it" is the day selected at 00hs in UTC, but when formatted, it is shown in GMT-3
                //Thus, it is necessary to compensate the days
                //TODO("Adapt this solution to a variable timeZone")
                val calendar = Calendar.getInstance(TimeZone.getDefault())
                calendar.time = Date(it)
                calendar.add(Calendar.DATE, 1)
                val selectedDate = calendar.time

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                val dateText = sdf.format(selectedDate)
                textInput.setText(dateText)
            }
        }
    }

    override fun setTimePicker(textInput: EditText?, tag: String) {
        textInput?.setOnClickListener {
            val selectedHour: Int = LocalDateTime.now().hour
            val selectedMinute: Int = LocalDateTime.now().minute

/*            if(textInput.hasSelection())
            {
                val time = Formatter.parseTime(textInput.text.toString())
                selectedHour = time.first
                selectedMinute = time.second
            } else {
                val nowTime = LocalDateTime.now()
                selectedHour = nowTime.hour
                selectedMinute = nowTime.minute
            }*/

            val timeP = MaterialTimePicker.Builder()
                    .setTimeFormat(TimeFormat.CLOCK_12H)
                    .setHour(selectedHour)
                    .setMinute(selectedMinute)
                    .setTitleText("Select reservation time")
                    .build()

            //TODO("Fix opening hour/minute when there was a selection")

            timeP.show((activity as AppCompatActivity).supportFragmentManager, tag)
            timeP.addOnPositiveButtonClickListener {
                //formatting time
                val sdf = SimpleDateFormat("hh:mm aaa", Locale.getDefault())

                val localDT = LocalDate.now().atTime(timeP.hour, timeP.minute)
                val date = Date.from(localDT.toInstant(ZoneOffset.ofHours(-3)))
                val timeText = sdf.format(date)
                textInput.setText(timeText)
            }
        }
    }

    override fun setDateTimePickers() {
        setDatePicker(binding.etStartDate.editText, "Start DatePicker")
        setDatePicker(binding.etEndDate.editText, "End DatePicker")
        setTimePicker(binding.etStartTime.editText, "Start TimePicker")
        setTimePicker(binding.etEndTime.editText, "End TimePicker")
    }
}