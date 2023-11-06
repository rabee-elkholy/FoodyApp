package com.androdu.foody.utils

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import com.androdu.foody.R
import com.google.android.material.snackbar.Snackbar

object HelperMethods {
    private var toast: Toast? = null
    private var snackbar: Snackbar? = null

    fun hasInternetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    fun showToastMessage(context: Context, msg: String) {
        if (toast != null) {
            toast?.cancel()
        }
        toast = Toast(context)
        toast!!.apply {
            setText(msg)
            duration = Toast.LENGTH_SHORT
            show()
        }
    }

    fun showSnackBarMessage(activity: Activity, msg: String) {
        if (snackbar != null) {
            snackbar?.dismiss()
        }
        snackbar =
            Snackbar.make(activity.findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG)
                .setAction(activity.getString(R.string.okay)) {}

        snackbar!!.show()
    }
}