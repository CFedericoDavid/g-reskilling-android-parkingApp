package com.fcaputo.parkingapp.mvp.model

import com.fcaputo.parkingapp.mvp.contract.SettingsContract
import com.fcaputo.parkingapp.utils.Constants
import com.fcaputo.parkingapp.utils.validation.ValidationErrorType
import com.fcaputo.parkingapp.utils.validation.ValidationResult

class SettingsModel : SettingsContract.Model {
    private var parkingSize: Int = Constants.SIZE_NOT_SET_INT

    override fun validate(size: Int): ValidationResult {
        return if (size == 0) {
            ValidationResult(isSuccess = false, ValidationErrorType.SIZE_IS_ZERO)
        } else {
            ValidationResult(isSuccess = true)
        }
    }

    override fun save(newSize: Int) { parkingSize = newSize }

    override fun getParkingSize(): Int = parkingSize
}
