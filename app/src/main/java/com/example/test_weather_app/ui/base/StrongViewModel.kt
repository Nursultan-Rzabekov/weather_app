package com.example.test_weather_app.ui.base

import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import com.example.test_weather_app.R
import com.example.test_weather_app.utils.showMessage
import io.reactivex.Single


class VmAction(
    var singleAction: (StrongActivity) -> Unit
) {

    fun invoke(activity: StrongActivity?) {
        activity ?: return
        singleAction(activity)
        singleAction = {}
    }
}

open class StrongViewModel : ViewModel(){
    val activityActionBehavior = SingleLiveEvent<VmAction>()

    @RequiresApi(Build.VERSION_CODES.M)
    fun VmAction.invokeAction() {
        val isUiThread =
            android.os.Looper.getMainLooper().isCurrentThread
        if (isUiThread) {
            activityActionBehavior.value = this
        } else {
            activityActionBehavior.postValue(this)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    protected open fun handleError(throwable: Throwable? = null, errorMessage: String? = null) {
        withActivity {
            showMessage(
                context = it,
                title = it.resources.getString(R.string.text_error),
                message = errorMessage ?: throwable?.localizedMessage.toString(),
                setCancelable = false,
                btnPositive = it.resources.getString(R.string.text_ok),
                btnPositiveEvent = DialogInterface.OnClickListener { dialog, _ ->
                    dialog.dismiss()
                }
            )
        }
    }

    open fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    }

    open fun onPermissionActivityResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {}

    @RequiresApi(Build.VERSION_CODES.M)
    fun Single<VmAction>.invoiceAction() = doOnSuccess { it.invokeAction() }

    @RequiresApi(Build.VERSION_CODES.M)
    fun withActivity(block: (StrongActivity) -> Unit){
        VmAction(block).invokeAction()
    }
}

