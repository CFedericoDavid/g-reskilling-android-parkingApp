package com.fcaputo.parkingapp.mvp.view

import android.app.Activity
import com.fcaputo.parkingapp.activity.ReleaseActivity
import com.fcaputo.parkingapp.activity.ReservationActivity
import com.fcaputo.parkingapp.activity.SettingsActivity
import com.fcaputo.parkingapp.databinding.ActivityHomeBinding
import com.fcaputo.parkingapp.mvp.contract.HomeContract
import com.fcaputo.parkingapp.mvp.view.base.ActivityView

class HomeView(activity: Activity) : ActivityView(activity), HomeContract.View {
    private var binding: ActivityHomeBinding = ActivityHomeBinding.inflate(activity.layoutInflater)

    init {
        activity.setContentView(binding.root)
    }

    override fun onSettingsButton(onPressed: () -> Unit) {
        binding.btnSettings.setOnClickListener { onPressed() }
    }

    override fun onMakeReservationButton(onPressed: () -> Unit) {
        binding.btnReservation.setOnClickListener { onPressed() }
    }

    override fun onReleaseButton(onPressed: () -> Unit) {
        binding.btnRelease.setOnClickListener { onPressed() }
    }

    override fun navigateToSettings() {
        activity?.startActivity(SettingsActivity.getIntent(activity))
    }

    override fun navigateToMakeReservation() {
        activity?.startActivity(ReservationActivity.getIntent(activity))
    }

    override fun navigateToReleaseSpot() {
        activity?.startActivity(ReleaseActivity.getIntent(activity))
    }
}
