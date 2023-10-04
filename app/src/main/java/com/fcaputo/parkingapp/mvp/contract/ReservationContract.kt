package com.fcaputo.parkingapp.mvp.contract

import com.fcaputo.parkingapp.entity.Reservation
import com.fcaputo.parkingapp.utils.DateTimePicker
import com.fcaputo.parkingapp.utils.validation.ValidationResult
import java.util.Calendar

interface ReservationContract {
    interface Model {
        fun getParkingLotSize(): Int
        fun storeReservation(reservation: Reservation)
        fun getReservations(): List<Reservation>
        fun validateData(startDate: Calendar, endDate: Calendar, parkingSpot: Int): ValidationResult
    }

    interface Presenter {
        fun onDateTimeClick(picker: DateTimePicker)
        fun onSaveButton()
    }

    interface View {
        fun showParkingLotSize(size: String)
        fun onSaveButton(onClick: () -> Unit)
        fun onDateTimeInputPressed(onInputClick: (DateTimePicker) -> Unit)
        fun showDatePicker(selectedDate: Calendar, pickerType: DateTimePicker)
        fun showTimePicker(selectedTime: Calendar, pickerType: DateTimePicker)
        fun getEditTextsContents(): List<String>
        fun getStartDate(): String
        fun getStartTime(): String
        fun getEndDate(): String
        fun getEndTime(): String
        fun getParkingLotNumber(): String
        fun getSecurityCode(): String
        fun showIncompleteFieldError()
        fun showPastDateTimesError()
        fun showUnorderedDateTimesError()
        fun showOutOfRangeSpotError()
        fun showZeroSpotError()
        fun showUnavailableSpotError()
        fun showSizeNotSetError()
        fun showSuccessMessage()
        fun clear()
    }
}
