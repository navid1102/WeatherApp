package com.navid.weatherapp.main.home

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Handler
import android.provider.Settings
import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.adtn.ghabzam.module.baseView.BaseFragment
import com.android.weathercore.core.weather.pojo.Hourly
import com.navid.weatherapp.R
import com.navid.weatherapp.databinding.FragmentHomeBinding
import com.navid.weatherapp.module.LocationProvider
import com.navid.weatherapp.module.alertNormal
import com.navid.weatherapp.module.alertNormal2Btn
import com.navid.weatherapp.module.baseView.GPSTracker
import com.navid.weatherapp.module.baseView.PermissionManager
import com.navid.weatherapp.module.iconCodToImage
import kotlin.math.roundToInt

class HomeFragment : BaseFragment<FragmentHomeBinding>(), PermissionManager.PermissionCallback,LocationProvider.LocationCallback {

    private var mLocationProvider: LocationProvider? = null


    val vm: HomePageViewModel = HomePageViewModel()
    private var adapter = HoursListAdapter()
    private var adapterDaily = DayListAdapter()


    override fun getLayoutResourceId(): Int {
        return R.layout.fragment_home
    }

    override fun oncreate() {

        binding.homePageViewModel = vm





        vm.getMyWeatherDataRequestLive.observe(this, Observer {
            it.let {

                initialHorizontalRecycler(it.hourly)
                binding.txtLocation.text=it.timezone.toString()
                binding.txtCurrentTemp.text=it.current.temp.roundToInt().toString()
                binding.imgTopIcon.setImageResource(
                    iconCodToImage(
                        it.current.weather[0].icon,
                        it.current.dt * 1000
                    )
                )
            }
        })


        vm.getMyDailyWeatherDataRequestLive.observe(this, Observer {
            var temp: com.android.weathercore.core.weather.pojo.daily.Temp =it.daily[0].temp
            initialVerticalRecycler(it.daily)
            var strMaxMinTemp=StringBuilder()
            strMaxMinTemp.append(temp.max.roundToInt().toString()).append("/").append(temp.min.roundToInt().toString())
            binding.txtMaxMinTemp.text=strMaxMinTemp.toString()
        })



    }

    private fun initialHorizontalRecycler(hourlyList: List<Hourly>) {
        binding.rcvHoursData.layoutManager =
            LinearLayoutManager(activity!!, RecyclerView.HORIZONTAL, false)
        binding.rcvHoursData.adapter = adapter
        adapter.updateData(hourlyList)

    }

    private fun initialVerticalRecycler(dailyList: ArrayList<com.android.weathercore.core.weather.pojo.daily.Daily>) {
        binding.rcvDailyData.layoutManager =
            LinearLayoutManager(activity!!, RecyclerView.VERTICAL, false)
        binding.rcvDailyData.adapter = adapterDaily
        dailyList.removeAt(0)
        adapterDaily.updateData(dailyList)
    }


    private fun checkLocation() {
        val lm = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gpsEnabled = false
        var networkEnabled = false

        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)

        } catch (e: Exception) {
        }

        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (ex: Exception) {
        }

        if (!gpsEnabled && !networkEnabled) {
            activity!!.alertNormal2Btn(R.drawable.ic_baseline_location_on_24,
                getString(R.string.turn_on_location),
                getString(R.string.turnOnGps)
                ,
                getString(R.string.enseraf),
                getString(R.string.turnOn),
                R.color.mdtp_ampm_text_color,
                false,
                {
                },
                {
                    startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                })
        }else if(gpsEnabled && networkEnabled){

        }
    }


    private fun myOnCreate() {
        mLocationProvider = activity?.let { LocationProvider(it, this) }
        mLocationProvider!!.connect()

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        @NonNull permissions: Array<String>,
        @NonNull grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            val handler = Handler()
            handler.postDelayed({ myOnCreate() }, 2000)


        } else {


        }
    }

    private fun permisionLocation() {

        if (ActivityCompat.checkSelfPermission(
                activity!!,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity!!, Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity!!,
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ),
                MY_LOCATION_REQUEST
            )
            return
        } else {
        }
    }

    override fun handleNewPermissionRequest(Success: Boolean?) {
        TODO("Not yet implemented")
    }

    companion object {


        private const val MY_LOCATION_REQUEST = 1100

    }

    override fun handleNewLocation(location: Location) {

        try {
            val currentLatitude = location.latitude
            val currentLongitude = location.longitude



        } catch (e: Exception) {

        }    }


    override fun onResume() {
        super.onResume()

        if (isOnline(context!!)){
            if (gpsNetWORKIsEnable()){
                var loc=Location("current")
                loc.latitude=GPSTracker(context!!).latitude
                loc.longitude=GPSTracker(context!!).longitude
                vm.getMyWeatherData(loc)
                vm.getMyDailyWeatherData()}else if(!gpsNetWORKIsEnable()){
                myOnCreate()
                permisionLocation()
                checkLocation()
            }
        }else{
            activity!!.alertNormal2Btn(R.drawable.ic_baseline_location_on_24,
                getString(R.string.titleInternet),
                getString(R.string.noInternrtConnectForUse)
                ,
                getString(R.string.enseraf),
                getString(R.string.reTry),
                R.color.mdtp_ampm_text_color,
                false,
                {
                },
                {
                    //todo
                    getDate()

                })
        }





    }


    fun gpsNetWORKIsEnable():Boolean{
        val lm = activity!!.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gpsEnabled = false
        var networkEnabled = false

        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)

        } catch (e: Exception) {
        }

        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (ex: Exception) {
        }
        return gpsEnabled && networkEnabled
    }


    fun isOnline(context: Context): Boolean {
        return try {
            val cm =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            //should check null because in airplane mode it will be null
            netInfo != null && netInfo.isConnected
        } catch (e: NullPointerException) {
            e.printStackTrace()
            false
        }
    }


    fun getDate(){
        if (isOnline(context!!)){
            if (gpsNetWORKIsEnable()){
                var loc=Location("current")
                loc.latitude=GPSTracker(context!!).latitude
                loc.longitude=GPSTracker(context!!).longitude
                vm.getMyWeatherData(loc)
                vm.getMyDailyWeatherData()}else if(!gpsNetWORKIsEnable()){
                myOnCreate()
                permisionLocation()
                checkLocation()
            }
        }else{
            activity!!.alertNormal2Btn(R.drawable.ic_baseline_location_on_24,
                getString(R.string.titleInternet),
                getString(R.string.noInternrtConnectForUse)
                ,
                getString(R.string.enseraf),
                getString(R.string.reTry),
                R.color.mdtp_ampm_text_color,
                false,
                {
                },
                {
                    //todo
                    getDate()

                })
        }
    }

}