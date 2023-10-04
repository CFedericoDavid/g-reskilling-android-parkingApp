package com.fcaputo.parkingapp.mvp.contract

interface HomeContract {
    interface Presenter {
        fun onSettingsClick()
        fun onMakeReservationClick()
    }

    interface View {
        fun onSettingsButton(onClick: () -> Unit)
        fun onMakeReservationButton(onClick: () -> Unit)
        fun navigateToSettings()
        fun navigateToMakeReservation()
    }
}
