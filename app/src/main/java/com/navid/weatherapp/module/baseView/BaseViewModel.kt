package com.navid.weatherapp.module.baseView

import android.content.res.Resources
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import com.android.weathercore.customs.MyShared
import com.navid.weatherapp.module.SingleLiveEvent

import org.koin.core.KoinComponent
import org.koin.core.inject

open class BaseViewModel : ViewModel(), KoinComponent, LifecycleObserver {
    val ms: MyShared by inject()
    val baseModel = BaseModel()
    val publicDialog = SingleLiveEvent<String?>()
    val res: Resources by inject()
}