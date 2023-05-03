package com.fcaputo.parkingapp.mvp.presenter

import com.fcaputo.parkingapp.mvp.contract.ParkingContract

class ParkingPresenter(private val model: ParkingContract.Model, private val view: ParkingContract.View) : ParkingContract.Presenter {
}