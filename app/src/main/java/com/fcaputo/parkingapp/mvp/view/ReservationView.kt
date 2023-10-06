package com.fcaputo.parkingapp.mvp.view

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.fcaputo.parkingapp.R
import com.fcaputo.parkingapp.databinding.ActivityReservationBinding
import com.fcaputo.parkingapp.mvp.contract.ReservationContract
import com.fcaputo.parkingapp.mvp.view.base.ActivityView
import com.fcaputo.parkingapp.utils.DateTimeCustomFormat
import com.fcaputo.parkingapp.utils.DateTimePicker
import com.fcaputo.parkingapp.utils.DateTimePicker.DATE_PICKER_END
import com.fcaputo.parkingapp.utils.DateTimePicker.DATE_PICKER_START
import com.fcaputo.parkingapp.utils.DateTimePicker.TIME_PICKER_END
import com.fcaputo.parkingapp.utils.DateTimePicker.TIME_PICKER_START
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.util.Calendar

class ReservationView(activity: Activity) : ActivityView(activity), ReservationContract.View {
    private var binding: ActivityReservationBinding = ActivityReservationBinding.inflate(activity.layoutInflater)

    init {
        activity.setContentView(binding.root)
    }

    override fun showParkingLotSize(size: String) {
        val sizeString = activity?.getString(R.string.reservation_parking_lot_size_helper, size)
        binding.tilParkingLot.helperText = sizeString
    }

    override fun onDateTimeInputPressed(onInputClick: (DateTimePicker) -> Unit) {
        binding.etStartDate.setOnClickListener { onInputClick(DATE_PICKER_START) }
        binding.etEndDate.setOnClickListener { onInputClick(DATE_PICKER_END) }
        binding.etStartTime.setOnClickListener { onInputClick(TIME_PICKER_START) }
        binding.etEndTime.setOnClickListener { onInputClick(TIME_PICKER_END) }
    }

    override fun showDatePicker(selectedDate: Calendar, pickerType: DateTimePicker) {
        val calendarConstraint = CalendarConstraints.Builder()
            .setValidator(DateValidatorPointForward.now())
            .build()

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setCalendarConstraints(calendarConstraint)
            .setSelection(selectedDate.timeInMillis)
            .build()

        datePicker.addOnPositiveButtonClickListener {
            val dateString = DateTimeCustomFormat.formatUtcDate(it)
            val editText = if (pickerType == DATE_PICKER_START) binding.etStartDate else binding.etEndDate
            editText.setText(dateString)
        }

        datePicker.show((activity as AppCompatActivity).supportFragmentManager, activity?.getString(R.string.date_picker_tag))
    }

    override fun showTimePicker(selectedTime: Calendar, pickerType: DateTimePicker) {
        val timePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_12H)
            .setHour(selectedTime[Calendar.HOUR_OF_DAY])
            .setMinute(selectedTime[Calendar.MINUTE])
            .build()

        timePicker.addOnPositiveButtonClickListener {
            val timeString = DateTimeCustomFormat.formatUtcTime(timePicker.hour, timePicker.minute)
            val editText = if (pickerType == TIME_PICKER_START) binding.etStartTime else binding.etEndTime
            editText.setText(timeString)
        }

        timePicker.show((activity as AppCompatActivity).supportFragmentManager, activity?.getString(R.string.time_picker_tag))
    }

    override fun getEditTextsContents(): List<String> = getEditTexts().map{ it.text.toString() }

    override fun onSaveButton(onClick: () -> Unit) {
        binding.submitButton.setOnClickListener { onClick() }
    }

    override fun getStartDate(): String = getStringFrom(binding.etStartDate)

    override fun getStartTime(): String = getStringFrom(binding.etStartTime)

    override fun getEndDate(): String = getStringFrom(binding.etEndDate)

    override fun getEndTime(): String = getStringFrom(binding.etEndTime)

    override fun getParkingLotNumber(): String = getStringFrom(binding.etParkingLot)

    override fun getSecurityCode(): String = getStringFrom(binding.etSecurityCode)

    private fun getStringFrom(editText: TextInputEditText) = editText.text.toString()

    private fun showErrorMessage(titleId: Int, messageId: Int) {
        MaterialAlertDialogBuilder(activity as AppCompatActivity)
            .setTitle(titleId)
            .setMessage(messageId)
            .setPositiveButton(R.string.error_dialog_got_it_text, null)
            .show()
    }

    override fun showIncompleteFieldError() {
        showErrorMessage(R.string.error_incomplete_field_title, R.string.error_incomplete_field_msg)
    }

    override fun showPastDateTimesError() {
        showErrorMessage(R.string.error_reservation_invalidDate_title, R.string.error_reservation_pastDates_msg)
    }

    override fun showUnorderedDateTimesError() {
        showErrorMessage(R.string.error_reservation_invalidDate_title, R.string.error_reservation_unorderedDates_msg)
    }

    override fun showOutOfRangeSpotError() {
        showErrorMessage(R.string.error_invalidSpot_title, R.string.error_reservation_outOfRangeSpot_msg)
    }

    override fun showZeroSpotError() {
        showErrorMessage(R.string.error_invalidSpot_title, R.string.error_spotNumberIsZero_msg)
    }

    override fun showUnavailableSpotError() {
        showErrorMessage(R.string.error_invalidSpot_title, R.string.error_reservation_unavailableSpot_msg)
    }

    override fun showSizeNotSetError() {
        showErrorMessage(R.string.error_others_title, R.string.error_size_not_set_msg)
    }

    override fun showSuccessMessage() {
        MaterialAlertDialogBuilder(activity as AppCompatActivity)
            .setTitle(R.string.reservation_success_title)
            .setMessage(R.string.reservation_success_msg)
            .setPositiveButton(R.string.success_dialog_ok_text, null)
            .show()
    }

    override fun clear() {
        binding.etStartDate.text?.clear()
        binding.etStartTime.text?.clear()
        binding.etEndDate.text?.clear()
        binding.etEndTime.text?.clear()
        binding.etParkingLot.text?.clear()
        binding.etSecurityCode.text?.clear()
        binding.root.requestFocus()
    }

    private fun getEditTexts() = listOf(
        binding.etStartDate,
        binding.etStartTime,
        binding.etEndDate,
        binding.etEndTime,
        binding.etParkingLot,
        binding.etSecurityCode
    )
}
