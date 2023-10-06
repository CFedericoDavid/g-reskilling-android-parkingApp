package com.fcaputo.parkingapp.mvp.contract

import com.fcaputo.parkingapp.utils.validation.ValidationResult

interface ReleaseContract {
    interface Model {
        fun validateData(parkingNumber: Int, securityCode: Int): ValidationResult
        fun deleteReservation(parkingNumber: Int, securityCode: Int)
    }

    interface Presenter {
        fun onReleasePressed()
        fun onReleaseConfirmed()
    }

    interface View {
        fun onReleaseButton(onPressed: () -> Unit)
        fun getEditTextsContents(): List<String>
        fun showIncompleteFieldError()
        fun showConfirmationDialog(onConfirmed: () -> Unit)
        fun getParkingNumber(): String
        fun getSecurityCode(): String
        fun showSuccessMessage()
        fun clear()
        fun showZeroSpotError()
        fun showOutOfRangeSpotError()
        fun showReservationNotFoundError()
        fun showGenericError()
    }
}
