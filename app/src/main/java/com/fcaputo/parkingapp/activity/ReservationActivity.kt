package com.fcaputo.parkingapp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fcaputo.parkingapp.R
import com.fcaputo.parkingapp.mvp.model.ReservationModel
import com.fcaputo.parkingapp.mvp.presenter.ReservationPresenter
import com.fcaputo.parkingapp.mvp.view.ReservationView

class ReservationActivity : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservation)
        ReservationPresenter(ReservationModel(), ReservationView(this))
    }

    companion object {
        fun getIntent(activity: Activity?): Intent {
            return Intent(activity as AppCompatActivity, ReservationActivity::class.java)
        }
    }
}
