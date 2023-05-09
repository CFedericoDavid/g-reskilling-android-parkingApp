package com.fcaputo.parkingapp.mvp.view

import android.app.Activity
import com.fcaputo.parkingapp.databinding.ActivityMainBinding
import com.fcaputo.parkingapp.entity.Reservation
import com.fcaputo.parkingapp.mvp.contract.ParkingContract
import com.fcaputo.parkingapp.mvp.view.base.ActivityView
import java.time.LocalDateTime

class ParkingView(activity: Activity) : ActivityView(activity), ParkingContract.View {

    private var binding: ActivityMainBinding = ActivityMainBinding.inflate(activity.layoutInflater)

    init {
        activity.setContentView(binding.root)
    }

    override fun onSaveButtonPressed(onClick: () -> Unit) {
        TODO("Not yet implemented")
    }

    override fun getStartDateTime(): LocalDateTime {
        TODO("Not yet implemented")
    }

    override fun getEndDateTime(): LocalDateTime {
        TODO("Not yet implemented")
    }

    override fun showDateTimeError() {
        TODO("Not yet implemented")
    }

    override fun getParkingLotNumber(): Int {
        TODO("Not yet implemented")
    }

    override fun getSecurityCode(): Int {
        TODO("Not yet implemented")
    }

    override fun showAvailabilityError() {
        TODO("Not yet implemented")
    }

    override fun showSuccessMessage(reservationInfo: Reservation) {
        TODO("Not yet implemented")
    }
}