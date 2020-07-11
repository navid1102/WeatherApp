package com.navid.weatherapp.module

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.mohamadamin.persianmaterialdatetimepicker.utils.PersianCalendar
import com.navid.weatherapp.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun iconCodToImage(iconCod: String, time: Long): Int {
    var img: Int? = null
    when (iconCod) {
        "01d", "01n" -> img=clearSky(getHours(time) ?:0)
        "02d", "02n" -> img=fewCloud(getHours(time) ?:0)
        "03d", "03n" -> img=R.drawable.ic_0_scattered_clouds
        "04d", "04n" -> img=R.drawable.ic_0_broken_cloud
        "09d", "09n" -> img=R.drawable.ic_0_shower_raint
        "10d", "10n" -> img=R.drawable.ic_0_rain
        "11d", "11n" -> img=R.drawable.ic_0__thunderstorm
        "13d", "13n" -> img=R.drawable.ic_0_snow
        "50d", "50n" -> img=R.drawable.ic_0_mist
    }
    return img?:R.drawable.ic_0_clear_sky_dawn

}
//timeConverter(time)

fun clearSky(hour: Int): Int {
    var img: Int? =0
    when (hour) {
        in 0..5 -> img=R.drawable.ic_0_clear_sky_dawn
        in 6..10 -> img=R.drawable.ic_0_clear_sky_morning
        in 11..15 -> img=R.drawable.ic_0_clear_sky_evening
        in 16..18 -> img=R.drawable.ic_0_clear_sky_evening
        in 19..23 -> img=R.drawable.ic_0_clear_sky_night
    }
    return img ?: R.drawable.ic_baseline_error_outline_24
}

fun fewCloud(hour: Int): Int {
    var img: Int? = null
    when (hour) {
        in 0..5 -> img=R.drawable.ic_0_few_cloud_dawn
        in 6..10 -> img=R.drawable.ic_0_few_cloud_morning
        in 11..15 -> img=R.drawable.ic_0_few_cloud_evening
        in 16..18 -> img=R.drawable.ic_0_few_cloud_evening
        in 19..23 -> img=R.drawable.ic_0_few_clound_night
    }
    return img ?: R.drawable.ic_baseline_error_outline_24
}

fun timeConverter(time: Long): Int {
    var persionCalendar=PersianCalendar()
    persionCalendar!!.timeInMillis = time.toLong() * 1000
    persionCalendar.time.hours

    var ss:Int=0

    ss=persionCalendar.persianDay
    return ss
}


private fun getHoursTime(s: Long): Int? {
    try {
        val sdf = SimpleDateFormat("HH")
        val netDate = Date(s.toLong())
        return sdf.format(netDate).toInt()
    } catch (e: Exception) {
        return e.toString().toInt()
    }
}

 fun getDateTime(s: Long): String? {
     try {
         val sdf = SimpleDateFormat("hh a",Locale.ENGLISH)
         val netDate = Date(s)
         return sdf.format(netDate)
     } catch (e: Exception) {
         return e.toString()
     }
}

fun getDailyDate(s: Long): Date? {
    val format =
        SimpleDateFormat("EEE', 'dd' 'MMM' 'yy' 'KK':'mm' 'aa") //dd/MM/yyyy

    return try {
        format.parse(s.toString())
    } catch (e: java.lang.Exception) {
        e.printStackTrace()
        null
    }
}

fun getHours(time:Long):Int{
    var cal= Calendar.getInstance()
    cal.timeInMillis=time

    return cal.time.hours

}


//------------------------------------------------------------------
//EEE MMM dd HH:mm:ss zzz yyyy

fun convertStringToDate(time: String?): Date? {
    val from =
        SimpleDateFormat("hh a", Locale.ENGLISH)
    try {
        return from.parse(time)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return Date()
}


fun getDyOfWeek(numberOfDay: Int): String? {
    val strDays = arrayOf(
        "Sunday",
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thurseday",
        "Friday",
        "Saturday"
    )
    var dayOfWeek: String? = null
    when (numberOfDay) {
        1 -> dayOfWeek = strDays[0]
        2 -> dayOfWeek = strDays[1]
        3 -> dayOfWeek = strDays[2]
        4 -> dayOfWeek = strDays[3]
        5 -> dayOfWeek = strDays[4]
        6 -> dayOfWeek = strDays[5]
        7 -> dayOfWeek = strDays[6]
    }
    return dayOfWeek
}


fun getIconDaily(iconCod: String): Int {
    var img: Int? = null
    when (iconCod) {
        "01d", "01n" -> img=R.drawable.ic_0_clear_sky_morning
        "02d", "02n" -> img=R.drawable.ic_0_few_cloud_morning
        "03d", "03n" -> img=R.drawable.ic_0_scattered_clouds
        "04d", "04n" -> img=R.drawable.ic_0_broken_cloud
        "09d", "09n" -> img=R.drawable.ic_0_shower_raint
        "10d", "10n" -> img=R.drawable.ic_0_rain
        "11d", "11n" -> img=R.drawable.ic_0__thunderstorm
        "13d", "13n" -> img=R.drawable.ic_0_snow
        "50d", "50n" -> img=R.drawable.ic_0_mist
    }
    return img?:R.drawable.ic_baseline_error_outline_24

}


const val REQUEST_LOCATION = 110

var locationPermissions = arrayOf(
    Manifest.permission.ACCESS_FINE_LOCATION,
    Manifest.permission.ACCESS_COARSE_LOCATION
)

fun hasLocationPermission(mContext: Context): Boolean {
    return ActivityCompat.checkSelfPermission(mContext, locationPermissions[0]) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(mContext, locationPermissions[1]) == PackageManager.PERMISSION_GRANTED

}

fun verifyPermissions(grantResults: IntArray): Boolean {
    // At least one result must be checked.
    if (grantResults.size < 1) {
        return false
    }

    // Verify that each required permission has been granted, otherwise return false.
    for (result in grantResults) {
        if (result != PackageManager.PERMISSION_GRANTED) {
            return false
        }
    }
    return true
}



