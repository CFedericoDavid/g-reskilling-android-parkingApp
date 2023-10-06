package com.fcaputo.parkingapp.mvp.view.utils

import android.content.Context
import androidx.annotation.StringRes
import com.google.android.material.dialog.MaterialAlertDialogBuilder

object AlertDialogFactory {
    fun showDialog(
        context: Context,
        @StringRes titleId: Int,
        @StringRes messageId: Int,
        @StringRes positiveTextId: Int,
        @StringRes negativeTextId: Int? = null,
        onConfirmed: (() -> Unit)? = null
    ) {
        val builder = MaterialAlertDialogBuilder(context)
            .setTitle(titleId)
            .setMessage(messageId)

        if (negativeTextId != null && onConfirmed != null) {
            builder.setPositiveButton(positiveTextId) { _, _ -> onConfirmed() }
                .setNegativeButton(negativeTextId, null)
        } else {
            builder.setPositiveButton(positiveTextId, null)
        }

        builder.show()
    }
}
