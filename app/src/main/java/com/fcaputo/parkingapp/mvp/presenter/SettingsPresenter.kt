package com.fcaputo.parkingapp.mvp.presenter

import com.fcaputo.parkingapp.mvp.contract.SettingsContract
import com.fcaputo.parkingapp.utils.Constants
import com.fcaputo.parkingapp.utils.validation.ValidationErrorType

class SettingsPresenter(private val model: SettingsContract.Model, private val view: SettingsContract.View) : SettingsContract.Presenter {
    init {
        view.onSaveButton { onSaveButton() }
        view.showParkingSize(getSizeString(model.getParkingSize()))
    }

    override fun onSaveButton() {
        if(formIsIncomplete()) {
            view.showErrorMessage(ValidationErrorType.INCOMPLETE_FIELD)
            return
        }

        val size = view.getParkingSize()
        val result = model.validate(size)
        if (result.isSuccess){
            model.save(size)
            view.showSuccessMessage()
            view.showParkingSize(getSizeString(model.getParkingSize()))
            view.clear()
        } else {
            result.error?.let { view.showErrorMessage(it) }
        }
    }

    private fun formIsIncomplete(): Boolean = view.getTextFieldsContents().any{ it.isNullOrEmpty() }
    private fun getSizeString(size: Int): String = if (size != Constants.ZERO_INT) size.toString() else Constants.NOT_SET_STRING
}
