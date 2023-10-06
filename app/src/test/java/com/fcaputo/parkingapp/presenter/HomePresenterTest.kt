package com.fcaputo.parkingapp.presenter

import com.fcaputo.parkingapp.mvp.contract.HomeContract
import com.fcaputo.parkingapp.mvp.presenter.HomePresenter
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class HomePresenterTest {
    private lateinit var presenter: HomeContract.Presenter
    private val view: HomeContract.View = mockk(relaxUnitFun = true)

    @Before
    fun setup() {
        presenter = HomePresenter(view)
        verify { view.onSettingsButton(any()) }
        verify { view.onMakeReservationButton(any()) }
    }

    @Test
    fun `when Settings button is pressed, presenter redirects to Settings screen`(){
        presenter.onSettingsPressed()
        verify { view.navigateToSettings() }
    }

    @Test
    fun `when 'Make a Reservation' button is pressed, presenter redirects to Reservations screen`(){
        presenter.onMakeReservationPressed()
        verify { view.navigateToMakeReservation() }
    }

}
