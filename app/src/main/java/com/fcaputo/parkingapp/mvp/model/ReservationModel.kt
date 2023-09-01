package com.fcaputo.parkingapp.mvp.model

import com.fcaputo.parkingapp.entity.Reservation
import com.fcaputo.parkingapp.mvp.contract.ReservationContract
import com.fcaputo.parkingapp.utils.Constants.ZERO_INT
import com.fcaputo.parkingapp.utils.validation.ValidationErrorType
import com.fcaputo.parkingapp.utils.validation.ValidationResult
import java.util.Calendar

class ReservationModel : ReservationContract.Model {
    private val reservations: MutableList<Reservation> = mutableListOf()

    override fun getParkingLotSize() = ParkingSettings.size

    override fun storeReservation(reservation: Reservation) {
        reservations.add(reservation)
    }

    override fun getReservations(): List<Reservation> = reservations.toList()

    override fun validateData(startDate: Calendar, endDate: Calendar, parkingSpot: Int): ValidationResult {
        val today = Calendar.getInstance()

        return if (getParkingLotSize() == ZERO_INT) {
            ValidationResult(isSuccess = false, ValidationErrorType.SIZE_NOT_SET)
        } else if (startDate.before(today) || endDate.before(today)) {
            ValidationResult(isSuccess = false, ValidationErrorType.DATETIMES_ARE_PAST)
        } else if (startDate.after(endDate)) {
            ValidationResult(isSuccess = false, ValidationErrorType.DATETIMES_ARE_UNORDERED)
        } else if (parkingSpot == 0) {
            ValidationResult(isSuccess = false, ValidationErrorType.SPOT_IS_ZERO)
        } else if (parkingSpot > getParkingLotSize()) {
            ValidationResult(isSuccess = false, ValidationErrorType.SPOT_IS_OUT_OF_RANGE)
        } else if (!isSpotAvailable(startDate, endDate, getReservationsByParkingSpot(parkingSpot))) {
            ValidationResult(isSuccess = false, ValidationErrorType.SPOT_IS_UNAVAILABLE)
        } else {
            ValidationResult(isSuccess = true)
        }
    }

    private fun getReservationsByParkingSpot(spotNumber: Int) = reservations.filter{ it.parkingLot == spotNumber }.toList()

    private fun isSpotAvailable(startDate: Calendar, endDate: Calendar, spotReservations: List<Reservation>): Boolean {
        return spotReservations.all { !datesOverlap(startDate, endDate, it) }
    }


    private fun datesOverlap(startDate: Calendar, endDate: Calendar, reservation: Reservation): Boolean {
        //Take the only two safe cases, which are when the candidate start date is after the reservation (therefore the end date will also be after it)
        // and when the candidate end date is before the reservation (therefore the start date will also precede it)
        // and negate the union to get the whole range of unsafe cases
        return ! (startDate.after(reservation.endDate) || endDate.before(reservation.startDate))
    }
}
