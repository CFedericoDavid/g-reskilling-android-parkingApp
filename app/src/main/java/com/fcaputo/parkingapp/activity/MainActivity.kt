package com.fcaputo.parkingapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fcaputo.parkingapp.R
import com.fcaputo.parkingapp.mvp.model.ParkingModel
import com.fcaputo.parkingapp.mvp.presenter.ParkingPresenter
import com.fcaputo.parkingapp.mvp.view.ParkingView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ParkingPresenter(ParkingModel(), ParkingView(this))
    }
}