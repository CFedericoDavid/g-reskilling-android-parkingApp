package com.fcaputo.parkingapp.mvp.contract

import com.fcaputo.parkingapp.utils.validation.ValidationErrorType
import com.fcaputo.parkingapp.utils.validation.ValidationResult

interface SettingsContract {

    interface Model {
        fun validate(size: Int): ValidationResult
        fun save(newSize: Int)
        fun getParkingSize(): Int
    }

    interface Presenter {
        fun onSaveButton()
    }

    interface View {
        fun onSaveButton(onClick: () -> Unit)
        fun getTextFieldsContents(): List<CharSequence?>
        fun getParkingSize(): Int
        fun showParkingSize(size: String)
        fun showErrorMessage(error: ValidationErrorType)
        fun showSuccessMessage()
        fun clear()
    }
}
