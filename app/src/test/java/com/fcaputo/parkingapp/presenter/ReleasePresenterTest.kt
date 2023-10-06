package com.fcaputo.parkingapp.presenter

import com.fcaputo.parkingapp.mvp.contract.ReleaseContract
import com.fcaputo.parkingapp.mvp.presenter.ReleasePresenter
import com.fcaputo.parkingapp.utils.Constants.EMPTY_STRING
import com.fcaputo.parkingapp.utils.validation.ValidationErrorType
import com.fcaputo.parkingapp.utils.validation.ValidationResult
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.Before
import org.junit.Test

class ReleasePresenterTest {
    private lateinit var presenter: ReleaseContract.Presenter
    private val view: ReleaseContract.View = mockk(relaxed = true)
    private val model: ReleaseContract.Model = mockk(relaxed = true)

    @Before
    fun setup() {
        presenter = ReleasePresenter(model, view)
        verify { view.onReleaseButton(any()) }
    }

    @Test
    fun `when release button is pressed, and form is incomplete, presenter raises an error`() {
        every { view.getEditTextsContents() } returns listOf(EMPTY_STRING, EMPTY_STRING)

        presenter.onReleasePressed()
        verify { view.showIncompleteFieldError() }
    }

    @Test
    fun `when release button is pressed, when validating, if an error occurs, presenter shows it`() {
        every { view.getEditTextsContents() } returns listOf(SPOT_STRING, SECURITY_CODE_STRING)
        every { view.getParkingNumber() } returns SPOT_STRING
        every { view.getSecurityCode() } returns SECURITY_CODE_STRING
        every { model.validateData(SPOT, SECURITY_CODE) } returns ValidationResult(isSuccess = false, error = ValidationErrorType.RESERVATION_NOT_FOUND)

        presenter.onReleasePressed()
        verifyOrder {
            view.getParkingNumber()
            view.getSecurityCode()
            model.validateData(SPOT, SECURITY_CODE)
            view.showReservationNotFoundError()
        }
    }

    @Test
    fun `when release button is pressed, when validating, if an unexpected error occurs, presenter shows a generic message`() {
        every { view.getEditTextsContents() } returns listOf(SPOT_STRING, SECURITY_CODE_STRING)
        every { view.getParkingNumber() } returns SPOT_STRING
        every { view.getSecurityCode() } returns SECURITY_CODE_STRING
        every { model.validateData(SPOT, SECURITY_CODE) } returns ValidationResult(isSuccess = false, error = ValidationErrorType.SIZE_NOT_SET)

        presenter.onReleasePressed()
        verifyOrder {
            view.getParkingNumber()
            view.getSecurityCode()
            model.validateData(SPOT, SECURITY_CODE)
            view.showGenericError()
        }
    }

    @Test
    fun `when release button is pressed, if data is correct, presenter shows confirmation dialog`() {
        every { view.getParkingNumber() } returns SPOT_STRING
        every { view.getSecurityCode() } returns SECURITY_CODE_STRING
        every { model.validateData(SPOT, SECURITY_CODE) } returns ValidationResult(isSuccess = true)

        presenter.onReleasePressed()
        verifyOrder {
            view.getParkingNumber()
            view.getSecurityCode()
            model.validateData(SPOT, SECURITY_CODE)
            view.showConfirmationDialog(any())
        }
    }

    @Test
    fun `after user confirmation, presenter releases the spot`() {
        every { view.getParkingNumber() } returns SPOT_STRING
        every { view.getSecurityCode() } returns SECURITY_CODE_STRING

        presenter.onReleaseConfirmed()
        verifyOrder {
            view.getParkingNumber()
            view.getSecurityCode()
            model.deleteReservation(SPOT, SECURITY_CODE)
            view.showSuccessMessage()
            view.clear()
        }
    }

        companion object {
        const val SPOT_STRING = "18"
        const val SECURITY_CODE_STRING = "1234"
        const val SPOT = 18
        const val SECURITY_CODE = 1234
    }
}
