package com.fcaputo.parkingapp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.fcaputo.parkingapp.R
import com.fcaputo.parkingapp.mvp.model.ReleaseModel
import com.fcaputo.parkingapp.mvp.presenter.ReleasePresenter
import com.fcaputo.parkingapp.mvp.view.ReleaseView

class ReleaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_release)
        ReleasePresenter(ReleaseModel(), ReleaseView(this))
    }

    companion object {
        fun getIntent(activity: Activity?): Intent {
            return Intent(activity as AppCompatActivity, ReleaseActivity::class.java)
        }
    }
}
