package com.fcaputo.parkingapp.mvp.contract

interface HomeContract {
    interface Presenter {
        fun onSettingsPressed()
        fun onMakeReservationPressed()
        fun onReleasePressed()
    }

    interface View {
        fun onSettingsButton(onPressed: () -> Unit)
        fun onMakeReservationButton(onPressed: () -> Unit)
        fun onReleaseButton(onPressed: () -> Unit)
        fun navigateToSettings()
        fun navigateToMakeReservation()
        fun navigateToReleaseSpot()
    }
}
