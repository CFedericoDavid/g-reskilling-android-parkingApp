package com.fcaputo.parkingapp.mvp.presenter

import com.fcaputo.parkingapp.mvp.contract.ParkingContract

class ParkingPresenter(private val model: ParkingContract.Model, private val view: ParkingContract.View) : ParkingContract.Presenter {

    init {
        view.onSaveButtonPressed { onSaveButtonPressed() }
    }

    override fun onSaveButtonPressed() {

    }

    override fun validateReservationDates(): Boolean {
        TODO("Not yet implemented")
    }

    override fun validateParkingLotAvailability(): Boolean {
        TODO("Not yet implemented")
    }
}