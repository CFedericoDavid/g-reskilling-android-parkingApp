package com.fcaputo.parkingapp.mvp.presenter

import com.fcaputo.parkingapp.entity.Reservation
import com.fcaputo.parkingapp.mvp.contract.ReservationContract
import com.fcaputo.parkingapp.utils.Constants
import com.fcaputo.parkingapp.utils.DateTimeCustomFormat
import com.fcaputo.parkingapp.utils.DateTimePicker
import com.fcaputo.parkingapp.utils.DateTimePicker.*
import com.fcaputo.parkingapp.utils.hasData
import com.fcaputo.parkingapp.utils.trimAtMinutes
import com.fcaputo.parkingapp.utils.validation.ValidationErrorType
import com.fcaputo.parkingapp.utils.validation.ValidationErrorType.*
import java.util.Calendar

class  ReservationPresenter(private val model: ReservationContract.Model, private val view: ReservationContract.View) : ReservationContract.Presenter {
    init {
        view.onSaveButton { onSaveButton() }
        view.onDateTimeInputPressed(this::onDateTimeClick)
        view.showParkingLotSize(getSizeString(model.getParkingLotSize()))
    }

    override fun onDateTimeClick(picker: DateTimePicker) {
        when (picker) {
            DATE_PICKER_START, DATE_PICKER_END -> {
                val text = if (picker == DATE_PICKER_START) view.getStartDate() else view.getEndDate()
                val date = if (text.hasData()) DateTimeCustomFormat.parseUtcDate(text) else Calendar.getInstance()
                view.showDatePicker(date.trimAtMinutes(), picker)
            }
            TIME_PICKER_START, TIME_PICKER_END -> {
                val text = if (picker == TIME_PICKER_START) view.getStartTime() else view.getEndTime()
                val time = if (text.hasData()) DateTimeCustomFormat.parseUtcTime(text) else Calendar.getInstance()
                view.showTimePicker(time.trimAtMinutes(), picker)
            }
        }
    }

    override fun onSaveButton() {
        if (!isFormComplete()) {
            showError(INCOMPLETE_FIELD)
            return
        }

        val startDateTime = getDateTimeFromString(view.getStartDate(), view.getStartTime())
        val endDateTime = getDateTimeFromString(view.getEndDate(), view.getEndTime())
        val parkingLot = view.getParkingLotNumber().toInt()
        val securityCode = view.getSecurityCode().toInt()

        val validationResult = model.validateData(startDateTime, endDateTime, parkingLot)
        if (validationResult.isSuccess) {
            model.storeReservation(Reservation(startDateTime, endDateTime, parkingLot, securityCode))
            view.showSuccessMessage()
            view.clear()
        } else {
            validationResult.error?.let { showError(it) }
        }
    }

    private fun isFormComplete(): Boolean = view.getEditTextsContents().all { it.hasData() }

    private fun getDateTimeFromString(sDate: String, sTime: String): Calendar {
        return DateTimeCustomFormat.parseLocalDateTime("$sDate $sTime")
    }

    private fun getSizeString(size: Int): String = if (size != Constants.ZERO_INT) size.toString() else Constants.NOT_SET_STRING

    private fun showError(error: ValidationErrorType) {
        when (error) {
            INCOMPLETE_FIELD -> view.showIncompleteFieldError()
            DATETIMES_ARE_PAST -> view.showPastDateTimesError()
            DATETIMES_ARE_UNORDERED -> view.showUnorderedDateTimesError()
            SPOT_IS_OUT_OF_RANGE -> view.showOutOfRangeSpotError()
            SPOT_IS_ZERO -> view.showZeroSpotError()
            SPOT_IS_UNAVAILABLE -> view.showUnavailableSpotError()
            SIZE_NOT_SET -> view.showSizeNotSetError()
            else -> return
        }
    }
}
