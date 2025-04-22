package com.example.evex.utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import com.example.evex.R

object Utils {

    private var dialog: AlertDialog? = null

    fun showVerificationDialog(context: Context) {
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_box, null)

        val builder = AlertDialog.Builder(context)
            .setView(view)
            .setCancelable(false)

        dialog = builder.create()
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.show()
    }

    fun dismissVerificationDialog() {
        dialog?.dismiss()
    }
}
