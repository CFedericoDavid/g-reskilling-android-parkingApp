package com.fcaputo.parkingapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fcaputo.parkingapp.R
import com.fcaputo.parkingapp.mvp.presenter.HomePresenter
import com.fcaputo.parkingapp.mvp.view.HomeView

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        HomePresenter(HomeView(this))
    }
}
