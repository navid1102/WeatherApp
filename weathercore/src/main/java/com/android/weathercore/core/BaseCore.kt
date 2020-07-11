package com.android.weathercore.core

import android.content.res.Resources
import com.android.weathercore.apiCalls.ApiInterface
import com.android.weathercore.apiCalls.ApiResponce
import com.android.weathercore.customs.MyShared
import org.koin.core.KoinComponent
import org.koin.core.inject


object BaseCore : KoinComponent {
    val ms: MyShared by inject()
    val response: ApiResponce by inject()
    val create: ApiInterface by inject()
    val res: Resources by inject()
}


