package com.example.test_weather_app.utils

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog

fun showMessage(context: Context, title: String? = null, message: String,
                btnNegative: String? = null, btnPositive: String = "OK",
                btnNegativeEvent: DialogInterface.OnClickListener? = null,
                btnPositiveEvent: DialogInterface.OnClickListener? = null,
                setCancelable: Boolean = true)
{
    var dialog: AlertDialog? = null
    val builder = AlertDialog.Builder(context)
    builder.setTitle(title?:"")
    builder.setMessage(message)
    builder.setCancelable(setCancelable)
    if (btnNegative != null) {
        if (btnNegativeEvent != null)
            builder.setNegativeButton(btnNegative, btnNegativeEvent)
        else
            builder.setNegativeButton(btnNegative) { _, _ ->
                dialog?.dismiss()
            }
    }
    builder.setPositiveButton(btnPositive, btnPositiveEvent)
    dialog = builder.create()
    dialog.show()
}

