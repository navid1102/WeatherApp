package com.navid.weatherapp.module

import androidx.fragment.app.FragmentActivity
import com.navid.weatherapp.R

fun FragmentActivity.alertNormal2Btn(
    icon: Int? = null,
    title: String? = null,
    message: String,
    titleBtn: String? = null,
    titleBtn2: String? = null,
    colorText: Int? = null,
    isClose: Boolean? = null,
    action1: (() -> Unit)? = null,
    action2: (() -> Unit)? = null
) {
    val fm = this.supportFragmentManager


    WeatherDialog.alertNormal2Btn(
        icon ?: R.drawable.ic_baseline_location_on_24,
        title ?: getString(R.string.baseErrorTitle),
        message,
        titleBtn ?: "بستن",
        titleBtn2 ?: "بستن",
        colorText ?: R.color.black,
        isClose ?: false,
        { action1?.invoke() },
        { action2?.invoke() }
    ).show(fm, "dialog")


}



fun FragmentActivity.alertNormal(
    icon: Int? = null,
    title: String? = null,
    message: String,
    titleBtn: String? = null,
    colorText: Int? = null,
    isClose: Boolean? = null,
    action1: (() -> Unit)? = null
) {
    val fm = this.supportFragmentManager
        WeatherDialog.alertNormal(
            icon ?: R.drawable.ic_baseline_error_outline_24,
            title ?: getString(R.string.baseErrorTitle),
            message,
            titleBtn ?: "close",
            colorText ?: R.color.black,
            isClose ?: false
        ) {
            action1?.invoke()
        }.show(fm, "dialog")

}
