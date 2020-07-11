package com.navid.weatherapp.module.baseView

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.ArrayList

class PermissionManager : ActivityCompat.OnRequestPermissionsResultCallback {

    private lateinit var mPermissionCallback: PermissionManager.PermissionCallback
    private lateinit var mContext: Activity

    constructor(context: Activity, callback: PermissionManager.PermissionCallback) {
        this@PermissionManager.mContext = context
        this@PermissionManager.mPermissionCallback = callback
    }

    constructor(context: Context, callback: GPSTracker) {}

    fun checkPermissions(requiredPermissions: Array<String>) {
        val arrRequiredPermissions = ArrayList<String>()
        for (rPermission in requiredPermissions) {
            if (ContextCompat.checkSelfPermission(mContext, rPermission) !== PackageManager.PERMISSION_GRANTED) {
                arrRequiredPermissions.add(rPermission)
            }
        }
        if (!arrRequiredPermissions.isEmpty()) {
            var reqPermsList = arrayOfNulls<String>(arrRequiredPermissions.size)
            reqPermsList = arrRequiredPermissions.toTypedArray()
            ActivityCompat.requestPermissions(
                mContext,
                reqPermsList,
                MY_PERMISSIONS_REQUEST
            )
        }
    }

    fun checkPermission(requiredPermission: String) {

        if (ContextCompat.checkSelfPermission(mContext, requiredPermission) !== PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    mContext,
                    requiredPermission
                )
            ) {
                //// TODO: 1/4/2017 Edit this code
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(
                    mContext,
                    arrayOf<String>(requiredPermission),
                    MY_PERMISSIONS_REQUEST
                )

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST -> {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // related task you need to do.
                    mPermissionCallback.handleNewPermissionRequest(true)

                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    mPermissionCallback.handleNewPermissionRequest(false)
                }
                return
            }
        }// other 'case' lines to check for other
        // permissions this app might request
    }

    interface PermissionCallback {
        fun handleNewPermissionRequest(Success: Boolean?)
    }

    companion object {
        val TAG = PermissionManager::class.java.simpleName
        private val MY_PERMISSIONS_REQUEST = 1
    }
}
