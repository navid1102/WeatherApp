package com.navid.weatherapp.main.home

import android.location.Location
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.OnLifecycleEvent
import com.android.weathercore.core.weather.Weather
import com.android.weathercore.core.weather.apiGetDailyWeatherData
import com.android.weathercore.core.weather.apiGetWeatherData
import com.android.weathercore.core.weather.pojo.WeatherDataResponse
import com.navid.weatherapp.R
import com.navid.weatherapp.module.SingleLiveEvent
import com.navid.weatherapp.module.baseView.BaseViewModel


class HomePageViewModel :BaseViewModel(),Weather{

    val getMyWeatherDataRequestLive by lazy{ MutableLiveData<WeatherDataResponse>()}
    val requestWeatherNullError by lazy{ SingleLiveEvent<String>()}

    val getMyDailyWeatherDataRequestLive by lazy{ MutableLiveData<WeatherDataResponse>()}
    val requestDailyWeatherNullError by lazy{ SingleLiveEvent<String>()}








    fun getMyWeatherData(location:Location){
        baseModel.showLoading()
        apiGetWeatherData("daily",location,{
            baseModel.stopLoading()
            getMyWeatherDataRequestLive.value=it
            baseModel.stopLoading()

        },{
            baseModel.stopLoading()
            getMyWeatherDataRequestLive.value=null
        })

    }




    fun getMyDailyWeatherData(){
        baseModel.showLoading()
        apiGetDailyWeatherData({
            baseModel.stopLoading()
            getMyDailyWeatherDataRequestLive.value=it

        },{ s: String, i: Int ->
            if (i==0){
                baseModel.stopLoading()
                requestWeatherNullError.value=s
            }
            else{
                baseModel.showError(res.getString(R.string.reTry),s){
                    getMyDailyWeatherData()}
            }

        })

    }

}