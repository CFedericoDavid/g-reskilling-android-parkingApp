package com.fcaputo.parkingapp.mvp.presenter

import com.fcaputo.parkingapp.mvp.contract.HomeContract

class HomePresenter(private val view: HomeContract.View) : HomeContract.Presenter {
    init {
        view.onSettingsButton { onSettingsClick() }
        view.onMakeReservationButton { onMakeReservationClick() }
    }

    override fun onSettingsClick() {
        view.navigateToSettings()
    }

    override fun onMakeReservationClick() {
        view.navigateToMakeReservation()
    }
}
