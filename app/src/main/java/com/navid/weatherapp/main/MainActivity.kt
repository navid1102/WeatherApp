package com.navid.weatherapp.main

import android.os.Build
import android.view.View
import com.adtn.ghabzam.module.baseView.BaseActivity
import com.navid.weatherapp.R
import com.navid.weatherapp.databinding.ActivityMainBinding
import com.navid.weatherapp.module.hasLocationPermission

class MainActivity : BaseActivity<ActivityMainBinding>() {


    override fun getLayoutResourceId(): Int {
        return R.layout.activity_main
    }

    override fun oncreate() {
    }

    override fun onResume() {
        super.onResume()

    }

}