package com.fcaputo.parkingapp.mvp.presenter

import com.fcaputo.parkingapp.entity.Reservation
import com.fcaputo.parkingapp.mvp.contract.ParkingContract
import java.time.LocalDateTime

class ParkingPresenter(private val model: ParkingContract.Model, private val view: ParkingContract.View) : ParkingContract.Presenter {

    init {
        view.onSaveButtonPressed { onSaveButtonPressed() }
    }

    override fun onSaveButtonPressed() {
        val startDateTime: LocalDateTime = view.getStartDateTime()
        val endDateTime: LocalDateTime = view.getEndDateTime()
        if (!model.areDateTimesValid(startDateTime, endDateTime)) {
            view.showDateTimeError()
            return
        }

        val parkingLot: Int = view.getParkingLotNumber()
        val securityCode: Int = view.getSecurityCode()
        if (!model.isParkingLotAvailable(parkingLot, startDateTime, endDateTime)){
            view.showAvailabilityError()
            return
        }

        val newReservation = Reservation(startDateTime, endDateTime, parkingLot, securityCode)
        model.storeReservation(newReservation)
        view.showSuccessMessage(newReservation)
    }

}