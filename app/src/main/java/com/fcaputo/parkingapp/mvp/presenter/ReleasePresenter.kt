package com.fcaputo.parkingapp.mvp.presenter

import com.fcaputo.parkingapp.mvp.contract.ReleaseContract
import com.fcaputo.parkingapp.utils.hasData
import com.fcaputo.parkingapp.utils.validation.ValidationErrorType
import com.fcaputo.parkingapp.utils.validation.ValidationErrorType.INCOMPLETE_FIELD
import com.fcaputo.parkingapp.utils.validation.ValidationErrorType.SPOT_IS_ZERO
import com.fcaputo.parkingapp.utils.validation.ValidationErrorType.SPOT_IS_OUT_OF_RANGE
import com.fcaputo.parkingapp.utils.validation.ValidationErrorType.RESERVATION_NOT_FOUND

class ReleasePresenter(private val model: ReleaseContract.Model, private val view: ReleaseContract.View) : ReleaseContract.Presenter {

    init {
        view.onReleaseButton { onReleasePressed() }
    }

    override fun onReleasePressed() {
        if(!isFormComplete()) {
            showError(INCOMPLETE_FIELD)
            return
        }

        val parkingNumber = view.getParkingNumber().toInt()
        val securityCode = view.getSecurityCode().toInt()

        val validationResult = model.validateData(parkingNumber, securityCode)
        if (validationResult.isSuccess) {
            view.showConfirmationDialog { onReleaseConfirmed() }
        } else {
            validationResult.error?.let { showError(it) }
        }
    }

    override fun onReleaseConfirmed() {
        val parkingNumber = view.getParkingNumber().toInt()
        val securityCode = view.getSecurityCode().toInt()

        model.deleteReservation(parkingNumber, securityCode)
        view.showSuccessMessage()
        view.clear()
    }


    private fun isFormComplete(): Boolean = view.getEditTextsContents().all { it.hasData() }

    private fun showError(error: ValidationErrorType) {
        when (error) {
            INCOMPLETE_FIELD -> view.showIncompleteFieldError()
            SPOT_IS_ZERO -> view.showZeroSpotError()
            SPOT_IS_OUT_OF_RANGE -> view.showOutOfRangeSpotError()
            RESERVATION_NOT_FOUND -> view.showReservationNotFoundError()
            else -> view.showGenericError()
        }
    }
}
