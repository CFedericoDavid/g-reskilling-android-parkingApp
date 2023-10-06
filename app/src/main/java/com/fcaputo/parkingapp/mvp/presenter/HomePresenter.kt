package com.fcaputo.parkingapp.mvp.presenter

import com.fcaputo.parkingapp.mvp.contract.HomeContract

class HomePresenter(private val view: HomeContract.View) : HomeContract.Presenter {
    init {
        view.onSettingsButton { onSettingsPressed() }
        view.onMakeReservationButton { onMakeReservationPressed() }
        view.onReleaseButton { onReleasePressed() }
    }

    override fun onSettingsPressed() {
        view.navigateToSettings()
    }

    override fun onMakeReservationPressed() {
        view.navigateToMakeReservation()
    }

    override fun onReleasePressed() {
        view.navigateToReleaseSpot()
    }
}
