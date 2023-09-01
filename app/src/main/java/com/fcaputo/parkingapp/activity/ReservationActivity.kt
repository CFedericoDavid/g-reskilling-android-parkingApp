package com.fcaputo.parkingapp.activity

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
}
