package com.android.weathercore.core.weather

import android.location.Location
import com.android.weathercore.BaseUri
import com.android.weathercore.R
import com.android.weathercore.apiCalls.ResponseState
import com.android.weathercore.core.BaseCore
import com.android.weathercore.core.weather.pojo.WeatherDataResponse

interface Weather
private val appid:String="fb2e37587677305ad1785e7731a066b4"

fun Weather.apiGetWeatherData(
    excludeItems:String,
    location: Location,
    weatherDataResponse: (WeatherDataResponse) -> Unit
    , errorState: (String) -> Unit
) {
    BaseCore.apply {
        val url = BaseUri.baseUrl + "/data/2.5/onecall?lat=${location.latitude}&lon=${location.longitude}&\n" +
                "exclude=${excludeItems}&units=metric&appid=${appid}"
        response.setCall(
            WeatherDataResponse::class.java,
            create.get(url),
            { responseState, data ->

                when (responseState) {
                    ResponseState.OKAY -> {
                        data?.let{weatherDataResponse(it)
                            android.util.Log.d("weatherHourly",
                                com.google.gson.GsonBuilder()
                                    .setPrettyPrinting().create().toJson(kotlin.Any()))}
                    }
                    ResponseState.InternetFailed -> {
                        android.util.Log.e("hourly", "InternetFailed map")
                        errorState(res.getString(R.string.internetError))
                    }
                    ResponseState.ListCountIsZERO -> {
                        android.util.Log.e("hourly", "ListCountIsZERO map")
                        errorState(res.getString(R.string.serverError))
                    }

                    ResponseState.ResponceNot200 -> {
                        errorState(res.getString(R.string.responceNot200))

                    }
                    ResponseState.ServerError -> {
                        android.util.Log.e("hourly", "ServerError map")
                        errorState(res.getString(R.string.serverError))
                    }

                    ResponseState.SuccessNot3 -> {
                        android.util.Log.e("hourly", "SuccessNot3 map")
                        errorState(res.getString(R.string.successNot3))
                    }

                }
            },
            "weatherHourly",returnedServerErrorMessage = {
                errorState(it)
            }
        )
    }

}



fun Weather.apiGetDailyWeatherData(
    weatherDataResponse: (WeatherDataResponse) -> Unit
    , errorState: (String,Int) -> Unit
) {
    BaseCore.apply {
        val url = BaseUri.baseUrl + "/data/2.5/onecall?lat=35.698238&lon=51.386062&\n" +
                "exclude=hourly&units=metric&appid=fb2e37587677305ad1785e7731a066b4"
        response.setCall(
            WeatherDataResponse::class.java,
            create.get(url),
            { responseState, data ->

                when (responseState) {
                    ResponseState.OKAY -> {
                        data?.let{weatherDataResponse(it)
                            android.util.Log.d("ghabzList",
                                com.google.gson.GsonBuilder()
                                    .setPrettyPrinting().create().toJson(kotlin.Any()))}
                    }
                    ResponseState.InternetFailed -> {
                        android.util.Log.e("map", "InternetFailed map")
                        errorState(res.getString(R.string.internetError),1)
                    }
                    ResponseState.ListCountIsZERO -> {
                        android.util.Log.e("map", "ListCountIsZERO map")
                        errorState(res.getString(R.string.serverError),0)
                    }

                    ResponseState.ResponceNot200 -> {
                        errorState(res.getString(R.string.responceNot200),1)

                    }
                    ResponseState.ServerError -> {
                        android.util.Log.e("map", "ServerError map")
                        errorState(res.getString(R.string.serverError),1)
                    }

                    ResponseState.SuccessNot3 -> {
                        android.util.Log.e("map", "SuccessNot3 map")
                        errorState(res.getString(R.string.successNot3),1)
                    }

                }
            },
            "getGhabzList",returnedServerErrorMessage = {
                errorState(it,1)

            }
        )
    }}
