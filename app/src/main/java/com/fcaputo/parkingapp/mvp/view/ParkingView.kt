package com.fcaputo.parkingapp.mvp.view

import android.app.Activity
import com.fcaputo.parkingapp.databinding.ActivityMainBinding
import com.fcaputo.parkingapp.mvp.contract.ParkingContract
import com.fcaputo.parkingapp.mvp.view.base.ActivityView
import java.lang.ref.WeakReference

class ParkingView(activity: Activity) : ActivityView(activity), ParkingContract.View {

    private var binding: ActivityMainBinding = ActivityMainBinding.inflate(activity.layoutInflater)

    init {
        activity.setContentView(binding.root)
    }
    override fun onSaveButtonPressed(onClick: () -> Unit) {
        TODO("Assign to UI Save button")
    }

    override fun getReservationDatesRange() {
        TODO("Not yet implemented")
    }

    override fun getParkingLotNumber(): Int {
        TODO("Not yet implemented")
    }

    override fun getSecurityCode(): Int {
        TODO("Not yet implemented")
    }


}