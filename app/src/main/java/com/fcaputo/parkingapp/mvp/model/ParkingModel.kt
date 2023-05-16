package com.fcaputo.parkingapp.mvp.model

import com.fcaputo.parkingapp.entity.Reservation
import com.fcaputo.parkingapp.mvp.contract.ParkingContract
import java.time.LocalDateTime

class ParkingModel : ParkingContract.Model {
    override fun storeReservation(reservation: Reservation) {
        TODO("Not yet implemented")
    }

    override fun isParkingLotAvailable(parkingLot: Int, startDate: LocalDateTime, endDate: LocalDateTime): Boolean {
        TODO("Not yet implemented")
    }

    override fun areDateTimesValid(startDate: LocalDateTime, endDate: LocalDateTime): Boolean = (startDate.isAfter(LocalDateTime.now()) && endDate.isAfter(LocalDateTime.now()))

}