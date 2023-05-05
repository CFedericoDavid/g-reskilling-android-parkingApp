package com.fcaputo.parkingapp.mvp.contract

interface ParkingContract {

    interface Model {
        fun storeReservation()
    }

    interface Presenter {
        fun onSaveButtonPressed()
        fun validateReservationDates() : Boolean
        fun validateParkingLotAvailability() : Boolean
    }

    /*
    - Fetch start & end date/time from UI
    - Validate dates/times
    - Fetch parking lot number & security code
    - Validate availability for parking lot
    - Build & store reservation
    */

    interface View {
        fun onSaveButtonPressed(onClick: () -> Unit)
        fun getReservationDatesRange()
        fun getParkingLotNumber() : Int
        fun getSecurityCode() : Int
    }
}