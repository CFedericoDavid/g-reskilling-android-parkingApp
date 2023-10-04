package com.fcaputo.parkingapp.presenter

import com.fcaputo.parkingapp.mvp.contract.ReservationContract
import com.fcaputo.parkingapp.mvp.presenter.ReservationPresenter
import com.fcaputo.parkingapp.utils.Constants.EMPTY_STRING
import com.fcaputo.parkingapp.utils.DateTimeCustomFormat
import com.fcaputo.parkingapp.utils.DateTimePicker
import com.fcaputo.parkingapp.utils.getUtcCalendarInstance
import com.fcaputo.parkingapp.utils.plusWeeks
import com.fcaputo.parkingapp.utils.trimAtMinutes
import com.fcaputo.parkingapp.utils.validation.ValidationErrorType
import com.fcaputo.parkingapp.utils.validation.ValidationResult
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.Before
import org.junit.Test
import java.util.Calendar

class ReservationPresenterTest {
    private lateinit var presenter: ReservationContract.Presenter
    private val view: ReservationContract.View = mockk(relaxed = true)
    private val model: ReservationContract.Model = mockk(relaxed = true)

    @Before
    fun setup() {
        presenter = ReservationPresenter(model, view)
        verifyOrder {
            view.onSaveButton(any())
            view.onDateTimeInputPressed(any())
            view.showParkingLotSize(any())
        }
    }

    @Test
    fun `when a date input is pressed in the UI, and a date was selected, presenter responds with a set date picker`() {
        every { view.getStartDate() } returns validDateString
        val date = DateTimeCustomFormat.parseUtcDate(validDateString)

        presenter.onDateTimeClick(DateTimePicker.DATE_PICKER_START)
        verify { view.showDatePicker(date, DateTimePicker.DATE_PICKER_START) }
    }

    @Test
    fun `when a date input is pressed in the UI, and no date was selected, presenter responds with a default date picker`() {
        every { view.getEndDate() } returns EMPTY_STRING
        //Here I don't use UTC timezone because I want the picker to show the current date
        //in the current timezone
        val date = Calendar.getInstance().trimAtMinutes()

        presenter.onDateTimeClick(DateTimePicker.DATE_PICKER_END)
        verify { view.showDatePicker(date, DateTimePicker.DATE_PICKER_END) }
    }

    @Test
    fun `when a time input is pressed in the UI, and a time was selected, presenter responds with a set time picker`() {
        every { view.getEndTime() } returns VALID_TIME_STRING
        val time = DateTimeCustomFormat.parseUtcTime(VALID_TIME_STRING)

        presenter.onDateTimeClick(DateTimePicker.TIME_PICKER_END)
        verify { view.showTimePicker(time, DateTimePicker.TIME_PICKER_END) }
    }

    @Test
    fun `when a time input is pressed in the UI, and no time was selected, presenter responds with a default time picker`() {
        every { view.getStartTime() } returns EMPTY_STRING
        //Here I don't use UTC timezone because I want the picker to show the current time
        //in the current timezone
        val time = Calendar.getInstance().trimAtMinutes()

        presenter.onDateTimeClick(DateTimePicker.TIME_PICKER_START)
        verify { view.showTimePicker(time, DateTimePicker.TIME_PICKER_START) }
    }

    @Test
    fun `when save button is pressed, and there's a missing field, presenter raises an error and shows it`() {
        every { view.getEditTextsContents() } returns listOf(validDateString, EMPTY_STRING, VALID_TIME_STRING)
        presenter.onSaveButton()
        verify {
            view.getEditTextsContents()
            view.showIncompleteFieldError()
        }
    }

    //This case covers only one error, as it tests the presenter flow.
    //The full range of errors is covered in the model UT
    @Test
    fun `when save button is pressed, and model returns an error, presenter shows it`() {
        val pastDateTime: Calendar = DateTimeCustomFormat.parseLocalDateTime(PAST_DATE_TIME_STRING)

        //Since the presenter has the parsing flow, the input from the view needs to be specified
        every { view.getStartDate() } returns PAST_DATE_STRING
        every { view.getStartTime() } returns MIDNIGHT_STRING
        every { view.getEndDate() } returns validDateString
        every { view.getEndTime() } returns VALID_TIME_STRING
        every { view.getParkingLotNumber() } returns PARKING_SPOT
        every { view.getSecurityCode() } returns SECURITY_CODE
        every { model.validateData(pastDateTime, any(), any()) } returns ValidationResult(false, ValidationErrorType.DATETIMES_ARE_PAST)

        presenter.onSaveButton()
        verifyOrder {
            view.getStartDate()
            view.getStartTime()
            view.getEndDate()
            view.getEndTime()
            view.getParkingLotNumber()
            view.getSecurityCode()
            model.validateData(pastDateTime, any(), any())
            view.showPastDateTimesError()
        }
    }

    @Test
    fun `when save button is pressed, and model returns an ok, presenter stores the reservation and shows a success message`() {
        every { view.getStartDate() } returns validDateString
        every { view.getStartTime() } returns MIDNIGHT_STRING
        every { view.getEndDate() } returns validDateString
        every { view.getEndTime() } returns VALID_TIME_STRING
        every { view.getParkingLotNumber() } returns PARKING_SPOT
        every { view.getSecurityCode() } returns SECURITY_CODE
        every { model.validateData(any(), any(), any()) } returns ValidationResult(true)

        presenter.onSaveButton()
        verifyOrder {
            view.getStartDate()
            view.getStartTime()
            view.getEndDate()
            view.getEndTime()
            view.getParkingLotNumber()
            view.getSecurityCode()
            model.validateData(any(), any(), any())
            model.storeReservation(any())
            view.showSuccessMessage()
            view.clear()
        }
    }

    companion object {
        const val PAST_DATE_STRING = "01/01/2023"
        const val MIDNIGHT_STRING = "12:00 AM"
        const val VALID_TIME_STRING = "2:00 AM"
        const val PARKING_SPOT = "100"
        const val SECURITY_CODE = "123"

        const val PAST_DATE_TIME_STRING = "$PAST_DATE_STRING $MIDNIGHT_STRING"
        private val validCalendar: Calendar = getUtcCalendarInstance().apply {
            plusWeeks(1)
            trimAtMinutes()
        }
        val validDateString = DateTimeCustomFormat.formatUtcDate(validCalendar.timeInMillis)
    }
}
