package com.fcaputo.parkingapp.mvp.model

import com.fcaputo.parkingapp.mvp.contract.ReleaseContract
import com.fcaputo.parkingapp.utils.Constants.ZERO_INT
import com.fcaputo.parkingapp.utils.validation.ValidationErrorType.SPOT_IS_ZERO
import com.fcaputo.parkingapp.utils.validation.ValidationErrorType.SPOT_IS_OUT_OF_RANGE
import com.fcaputo.parkingapp.utils.validation.ValidationErrorType.RESERVATION_NOT_FOUND
import com.fcaputo.parkingapp.utils.validation.ValidationResult

class ReleaseModel : ReleaseContract.Model {
    private val reservations = ParkingData.reservations

    override fun validateData(parkingNumber: Int, securityCode: Int): ValidationResult {
        return if (parkingNumber == ZERO_INT) {
            ValidationResult(isSuccess = false, error = SPOT_IS_ZERO)
        } else if (parkingNumber > getParkingLotSize()) {
            ValidationResult(isSuccess = false, error = SPOT_IS_OUT_OF_RANGE)
        } else if (!reservationExists(parkingNumber, securityCode)) {
            ValidationResult(isSuccess = false, error = RESERVATION_NOT_FOUND)
        } else {
            ValidationResult(isSuccess = true)
        }
    }

    private fun reservationExists(parkingNumber: Int, securityCode: Int): Boolean {
        return reservations.any { it.parkingLot == parkingNumber && it.securityCode == securityCode }
    }

    private fun getParkingLotSize() = ParkingSettings.size

    override fun deleteReservation(parkingNumber: Int, securityCode: Int) {
        reservations.removeIf { it.parkingLot == parkingNumber && it.securityCode == securityCode }
    }
}
