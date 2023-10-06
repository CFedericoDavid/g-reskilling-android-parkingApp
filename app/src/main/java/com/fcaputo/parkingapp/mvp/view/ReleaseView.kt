package com.fcaputo.parkingapp.mvp.view

import android.app.Activity
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.fcaputo.parkingapp.R
import com.fcaputo.parkingapp.databinding.ActivityReleaseBinding
import com.fcaputo.parkingapp.mvp.contract.ReleaseContract
import com.fcaputo.parkingapp.mvp.view.base.ActivityView
import com.fcaputo.parkingapp.mvp.view.utils.AlertDialogFactory

class ReleaseView(activity: Activity) : ActivityView(activity), ReleaseContract.View {
    private var binding: ActivityReleaseBinding = ActivityReleaseBinding.inflate(activity.layoutInflater)

    init {
        activity.setContentView(binding.root)
    }

    override fun onReleaseButton(onPressed: () -> Unit) {
        binding.btnRelease.setOnClickListener { onPressed() }
    }

    override fun getEditTextsContents(): List<String> {
        return listOf(binding.etParkingNumber.text.toString(),
            binding.etSecurityCode.text.toString()
        )
    }

    override fun showIncompleteFieldError() {
        showErrorMessage(R.string.error_incomplete_field_title, R.string.error_incomplete_field_msg)
    }

    override fun showConfirmationDialog(onConfirmed: () -> Unit) {
        AlertDialogFactory.showDialog(
            activity as AppCompatActivity,
            R.string.release_confirmation_title,
            R.string.release_confirmation_msg,
            R.string.release_confirmation_yes,
            R.string.release_confirmation_no
        ) { onConfirmed() }
    }

    override fun getParkingNumber(): String = binding.etParkingNumber.text.toString()

    override fun getSecurityCode(): String = binding.etSecurityCode.text.toString()

    override fun showSuccessMessage() {
        AlertDialogFactory.showDialog(
            activity as AppCompatActivity,
            R.string.release_success_title,
            R.string.release_success_msg,
            R.string.success_dialog_ok_text
        )
    }

    override fun clear() {
        binding.etParkingNumber.text?.clear()
        binding.etSecurityCode.text?.clear()
        binding.root.requestFocus()
    }

    override fun showZeroSpotError() {
        showErrorMessage(R.string.error_invalidSpot_title, R.string.error_spotNumberIsZero_msg)
    }

    override fun showOutOfRangeSpotError() {
        showErrorMessage(R.string.error_invalidSpot_title, R.string.error_release_outOfRangeSpot_msg)
    }

    override fun showReservationNotFoundError() {
        showErrorMessage(R.string.error_release_reservationNotFound_title, R.string.error_release_reservationNotFound_msg)
    }

    override fun showGenericError() {
        showErrorMessage(R.string.error_others_title, R.string.error_others_msg)
    }

    private fun showErrorMessage(@StringRes titleId: Int, @StringRes messageId: Int) {
        AlertDialogFactory.showDialog(
            activity as AppCompatActivity,
            titleId,
            messageId,
            R.string.error_dialog_got_it_text
        )
    }
}
