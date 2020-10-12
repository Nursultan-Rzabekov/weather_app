package com.example.test_weather_app.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AlertDialog
import kotlin.reflect.KClass

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

fun <T : Activity> Activity.startNewActivity(activityClass: KClass<T>, block: Intent.() -> Unit = {}){
    val intent = Intent(this, activityClass.java)
    intent.block()
    this.startActivity(intent)
}


