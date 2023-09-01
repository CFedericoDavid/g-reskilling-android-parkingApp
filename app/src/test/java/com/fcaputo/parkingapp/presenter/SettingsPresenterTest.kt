package com.fcaputo.parkingapp.presenter

import com.fcaputo.parkingapp.mvp.contract.SettingsContract
import com.fcaputo.parkingapp.mvp.model.SettingsModel
import com.fcaputo.parkingapp.mvp.presenter.SettingsPresenter
import com.fcaputo.parkingapp.utils.Constants
import com.fcaputo.parkingapp.utils.validation.ValidationErrorType
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.junit.Before
import org.junit.Test

class SettingsPresenterTest {
    private lateinit var presenter: SettingsContract.Presenter
    private val view: SettingsContract.View = mockk(relaxUnitFun = true)

    @Before
    fun setup() {
        presenter = SettingsPresenter(SettingsModel(), view)
        verify { view.onSaveButton(any()) }
        verify { view.showParkingSize(any()) }
    }

    @Test
    fun `when save button is pressed, and no size is set, the presenter raises an error and the view shows it`(){
        every { view.getTextFieldsContents() } returns listOf(Constants.EMPTY_STRING)
        presenter.onSaveButton()
        verifyOrder {
            view.getTextFieldsContents()
            view.showErrorMessage(ValidationErrorType.INCOMPLETE_FIELD)
        }
    }

    @Test
    fun `when save button is pressed, and an invalid number is set, model raises an error and view shows it`(){
        every { view.getTextFieldsContents() } returns listOf(ZERO_STRING)
        every { view.getParkingSize() } returns Constants.ZERO_INT
        presenter.onSaveButton()
        verifyOrder {
            view.getTextFieldsContents()
            view.getParkingSize()
            view.showErrorMessage(ValidationErrorType.SIZE_IS_ZERO)
        }
    }

    @Test
    fun `when save button is pressed, and a valid number is set, model updates the size and view shows it`(){
        every { view.getTextFieldsContents() } returns listOf(VALID_SIZE_STRING)
        every { view.getParkingSize() } returns VALID_SIZE_INT
        presenter.onSaveButton()
        verifyOrder {
            view.getTextFieldsContents()
            view.showSuccessMessage()
            view.showParkingSize(VALID_SIZE_STRING)
            view.clear()
        }
    }

    companion object {
        const val ZERO_STRING = "0"
        const val VALID_SIZE_INT = 100
        const val VALID_SIZE_STRING = "100"
    }
}
