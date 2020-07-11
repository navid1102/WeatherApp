package com.navid.weatherapp.module.baseView

import android.Manifest
import android.app.Service
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat


class GPSTracker(private val context: Context) : Service(), LocationListener, PermissionManager.PermissionCallback {

    internal var isGPSEnabled = false
    internal var isNetworkEnabled = false
    var canGetLocation = false
    internal lateinit var permissionManager: PermissionManager
    internal var location: Location? = null


    internal var latitude: Double = 0.toDouble()
    internal var longitude: Double = 0.toDouble()

    protected var locationManager: LocationManager? = null

    init {
        getLocation()
    }

    fun getLocation(): Location? {
        try {
            locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)

            isNetworkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)

            if (!isGPSEnabled && !isNetworkEnabled) {

            } else {
                this.canGetLocation = true

                if (isNetworkEnabled) {

                    locationManager!!.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this
                    )

                    if (locationManager != null) {
                        location = locationManager!!
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                        if (location != null) {

                            latitude = location!!.latitude
                            longitude = location!!.longitude
                        }
                    }

                }

                if (isGPSEnabled) {
                    if (location == null) {
                        permissionManager = PermissionManager(context, this)
                        permissionManager.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        permissionManager.checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)

                        locationManager!!.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this
                        )
                        if (locationManager != null) {
                            location = locationManager!!.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                            if (location != null) {
                                latitude = location!!.latitude
                                longitude = location!!.longitude
                            }
                        }
                    }
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

        return location
    }


    fun stopUsingGPS() {
        if (locationManager != null) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) !== PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) !== PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            locationManager!!.removeUpdates(this@GPSTracker)
        }
    }

    fun getLatitude(): Double {
        if (location != null) {
            latitude = location!!.latitude
        }
        return latitude
    }

    fun getLongitude(): Double {
        if (location != null) {
            longitude = location!!.longitude
        }

        return longitude
    }

    fun canGetLocation(): Boolean {
        return this.canGetLocation
    }

    fun showSettingsAlert() {
        val alertDialog = AlertDialog.Builder(context)

        alertDialog.setTitle("GPS is settings")

        alertDialog.setMessage("Turn on your GPS to find nearby helpers")

        alertDialog.setPositiveButton("Settings", DialogInterface.OnClickListener { dialog, which ->
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            context.startActivity(intent)
        })

        alertDialog.setNegativeButton("Cancel",
            DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })

        alertDialog.show()
    }

    override fun onLocationChanged(arg0: Location) {
        // TODO Auto-generated method stub

    }

    override fun onProviderDisabled(arg0: String) {
        // TODO Auto-generated method stub

    }

    override fun onProviderEnabled(arg0: String) {
        // TODO Auto-generated method stub

    }

    override fun onStatusChanged(arg0: String, arg1: Int, arg2: Bundle) {
        // TODO Auto-generated method stub

    }

    override fun onBind(intent: Intent): IBinder? {
        // TODO Auto-generated method stub
        return null
    }

    override fun handleNewPermissionRequest(Success: Boolean?) {

    }

    companion object {

        private val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10
        private val MIN_TIME_BW_UPDATES = (1000 * 60 * 1).toLong()
    }


}
