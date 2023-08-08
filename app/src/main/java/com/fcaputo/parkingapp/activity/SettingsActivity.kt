package com.fcaputo.parkingapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fcaputo.parkingapp.R
import com.fcaputo.parkingapp.mvp.model.SettingsModel
import com.fcaputo.parkingapp.mvp.presenter.SettingsPresenter
import com.fcaputo.parkingapp.mvp.view.SettingsView

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        SettingsPresenter(SettingsModel(), SettingsView(this))
    }
}
