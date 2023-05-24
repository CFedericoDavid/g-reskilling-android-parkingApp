package com.fcaputo.parkingapp.mvp.contract

import android.widget.EditText
import com.fcaputo.parkingapp.entity.Reservation
import java.time.LocalDateTime

interface ParkingContract {

    interface Model {
        fun storeReservation(reservation: Reservation)
        fun isParkingLotAvailable(parkingLot: Int, startDate: LocalDateTime, endDate: LocalDateTime): Boolean
        fun areDateTimesValid(startDate: LocalDateTime, endDate: LocalDateTime): Boolean
    }

    interface Presenter {
        fun onSaveButtonPressed()
    }

    interface View {
        fun onSaveButtonPressed(onClick: () -> Unit)
        fun getStartDateTime(): LocalDateTime
        fun getEndDateTime(): LocalDateTime
        fun showDateTimeError()
        fun getParkingLotNumber(): Int
        fun getSecurityCode(): Int
        fun showAvailabilityError()
        fun showSuccessMessage(reservationInfo: Reservation)
        fun setDatePicker(textInput: EditText?, tag: String)
        fun setTimePicker(textInput: EditText?, tag: String)
        fun setDateTimePickers()
    }
}