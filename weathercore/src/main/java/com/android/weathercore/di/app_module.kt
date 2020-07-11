package com.android.weathercore.di

import android.content.Context
import com.android.weathercore.apiCalls.ApiInterface
import com.android.weathercore.apiCalls.ApiResponce
import com.android.weathercore.apiCalls.ServiceGenerator
import com.android.weathercore.customs.MyShared
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sdkModule = module {
    single { val myShared = MyShared(androidContext())
        myShared
    }
    single { ApiResponce() }
    single { apiInterface(get()) }
    single { androidContext().resources }
}

    fun apiInterface(context: Context): ApiInterface {
        return ServiceGenerator.createService(ApiInterface::class.java, MyShared(context))
    }




