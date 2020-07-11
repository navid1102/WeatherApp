package com.navid.weatherapp.main


import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.android.weathercore.Server
import com.android.weathercore.UrlType
import com.batch.android.Batch
import com.batch.android.BatchActivityLifecycleHelper
import com.batch.android.Config




class App : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()

        Server.setServerType(UrlType.Public)

    }
}