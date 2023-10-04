package com.fcaputo.parkingapp.mvp.view

import android.app.Activity
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fcaputo.parkingapp.R
import com.fcaputo.parkingapp.databinding.ActivitySettingsBinding
import com.fcaputo.parkingapp.mvp.contract.SettingsContract
import com.fcaputo.parkingapp.mvp.view.base.ActivityView
import com.fcaputo.parkingapp.utils.validation.ValidationErrorType
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class SettingsView(activity: Activity) : ActivityView(activity),SettingsContract.View {
    private var binding: ActivitySettingsBinding = ActivitySettingsBinding.inflate(activity.layoutInflater)

    init {
        activity.setContentView(binding.root)
    }

    override fun onSaveButton(onClick: () -> Unit) {
        binding.saveButton.setOnClickListener{ onClick() }
    }

    override fun getTextFieldsContents(): List<CharSequence?> = listOf(binding.textInputParkingSize).map{ it.editText?.text }

    override fun clear() {
        binding.textInputParkingSize.editText?.text?.clear()
        binding.root.requestFocus()
    }

    override fun getParkingSize(): Int = binding.textInputParkingSize.editText?.text.toString().toInt()

    override fun showParkingSize(size: String) {
        val sizeString = activity?.resources?.getString(R.string.settings_current_size, size)
        binding.textInputParkingSize.helperText = sizeString
    }

    override fun showErrorMessage(error: ValidationErrorType) {
        val (titleId, messageId) = getTitleMessageFromError(error)

        MaterialAlertDialogBuilder(activity as AppCompatActivity)
            .setTitle(titleId)
            .setMessage(messageId)
            .setPositiveButton(R.string.error_dialog_got_it_text, null)
            .show()
    }

    private fun getTitleMessageFromError(error: ValidationErrorType): Pair<Int, Int> {
        return when(error){
            ValidationErrorType.INCOMPLETE_FIELD -> {
                Pair(R.string.error_incomplete_field_title, R.string.error_incomplete_field_msg)
            }
            ValidationErrorType.SIZE_IS_ZERO -> {
                Pair(R.string.error_settings_sizeIsZero_title, R.string.error_settings_sizeIsZero_msg)
            }
            else -> { Pair(Int.MIN_VALUE, Int.MIN_VALUE) }
        }
    }

    override fun showSuccessMessage() {
        MaterialAlertDialogBuilder(activity as AppCompatActivity)
            .setMessage(R.string.settings_success_msg)
            .setPositiveButton(R.string.success_dialog_ok_text, null)
            .show()
    }
}
