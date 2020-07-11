package com.android.weathercore.apiCalls

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.android.weathercore.apiCalls.ResponseState
import com.android.weathercore.apiCalls.Utils
import com.google.gson.JsonObject
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback


@Suppress("UNNECESSARY_SAFE_CALL")
open class ApiResponce {
    companion object {
        val liveExpire = MutableLiveData<Boolean>()

    }

    var call: Call<JsonObject>? = null
    fun <T> setCall(
        tClass: Class<T>?,
        call: Call<JsonObject>?,
        serverListener: (ResponseState, data: T?) -> Unit,
        methodName: String = "",
        returnedServerErrorMessage: ((String) -> Unit)? = null,
        returnedServerErrorMessageAndStatus: ((String?,Int?) -> Unit)? = null
    ) {

        this.call = call
        call?.enqueue(object : Callback<JsonObject> {

            override fun onResponse(call: Call<JsonObject>, response: retrofit2.Response<JsonObject>) {
                when {
                    response.isSuccessful -> {

                        var jsonObject: JSONObject? = null
                        try {
                            jsonObject = JSONObject(response.body()?.toString())
                        } catch (e: JSONException) {

                            e.printStackTrace()

                        }

                        if (methodName == ("checkStatus")) {
                            if (jsonObject?.getInt("statusCode") == 3) {


                                serverListener(
                                    ResponseState.OKAY,
                                    tClass?.let { Utils.mapperTojson(tClass, response.body()) } ?: run { null }
                                )

                                return
                            } else {
                                serverListener(
                                    ResponseState.SuccessNot3,
                                    tClass?.let { Utils.mapperTojson(tClass, response.body()) } ?: run { null }
                                )
                                return
                            }
                        }

                        if (methodName == "login") {
                            try {
                                if (jsonObject?.getInt("statusCode") != 3) {

                                    serverListener(
                                        ResponseState.SuccessNot3,
                                        tClass?.let { Utils.mapperTojson(tClass, response.body()) } ?: run { null }
                                    )

                                    if (!jsonObject?.getString("additionalInfo").isNullOrEmpty()) {
                                        returnedServerErrorMessage?.let {
                                            it(
                                                jsonObject?.getString("additionalInfo")
                                                    ?: "مشکلی پیش آمده است.مجدد تلاش نمایید"
                                            )
                                            return
                                        }
                                        returnedServerErrorMessageAndStatus?.let {
                                            it(
                                                jsonObject?.getString("additionalInfo")
                                                    ?: "مشکلی پیش آمده است.مجدد تلاش نمایید",jsonObject?.getInt("statusCode")
                                            )
                                            return
                                        }

                                        serverListener(
                                            ResponseState.OKAY,
                                            tClass?.let { Utils.mapperTojson(tClass, response.body()) } ?: run { null })

                                    }
                                    return
                                }
                            } catch (e: Exception) {

                            }

                            serverListener(
                                ResponseState.OKAY,
                                tClass?.let { Utils.mapperTojson(tClass, response.body()) } ?: run { null })
                            return

                        }


                        try {
                            if (jsonObject?.getInt("statusCode") == 3) {

                                if (jsonObject?.getJSONArray("data").length() > 0) {
                                    serverListener(
                                        ResponseState.OKAY,
                                        tClass?.let { Utils.mapperTojson(tClass, response.body()) }
                                            ?: run { null }
                                    )

                                }
                                else {

                                    try {
                                        if (!jsonObject?.getString("additionalInfo").isNullOrEmpty() ) {

                                            Log.e("navid",jsonObject?.getString("additionalInfo"))
                                            returnedServerErrorMessage?.let {
                                                it(jsonObject?.getString("additionalInfo"))
                                                return

                                            }
                                            returnedServerErrorMessageAndStatus?.let {
                                                it(jsonObject?.getString("additionalInfo"),jsonObject?.getInt("statusCode"))
                                                return
                                            }
                                            serverListener(
                                                ResponseState.ListCountIsZERO,
                                                tClass?.let { Utils.mapperTojson(tClass, response.body()) }
                                                    ?: run { null })
                                        }
                                    } catch (e: java.lang.Exception) {

                                        serverListener(
                                            ResponseState.ListCountIsZERO,
                                            tClass?.let { Utils.mapperTojson(tClass, response.body()) }
                                                ?: run { null })
                                    }



                                }



                                return


                            }
                        } catch (e: Exception) {
                            try {
                                serverListener(
                                    ResponseState.OKAY,
                                    tClass?.let { Utils.mapperTojson(tClass, response.body()) }
                                        ?: run { null })
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                            return


                        }




                        try {
                            if (!jsonObject?.getString("additionalInfo").isNullOrEmpty()) {
                               returnedServerErrorMessage?.let {

                                           it(jsonObject?.getString("additionalInfo") ?: "مشکلی پیش آمده است.مجدد تلاش نمایید")
                                   return
                               }
                                returnedServerErrorMessageAndStatus?.let {
                                    it(jsonObject?.getString("additionalInfo"),jsonObject?.getInt("statusCode"))
                                    return
                                }
                                serverListener(
                                    ResponseState.SuccessNot3,
                                    tClass?.let { Utils.mapperTojson(tClass, response.body()) } ?: run { null })
                            }
                        } catch (e: java.lang.Exception) {

                            serverListener(
                                ResponseState.SuccessNot3,
                                tClass?.let { Utils.mapperTojson(tClass, response.body()) } ?: run { null })
                        }

                        return

                    }
                    response.code() == 401 ->if(methodName == "Pushh"){ serverListener(ResponseState.ServerError, null) } else { liveExpire.value = true}
                    response.code() == 404 ->if(methodName == "Pushh"){ serverListener(ResponseState.ServerError, null) } else { liveExpire.value = true}
                    response.code() >= 500 -> serverListener(ResponseState.ServerError, null)

                    else -> {
                        serverListener(ResponseState.ResponceNot200, tClass?.let {
                            Utils.mapperTojson(tClass, response.errorBody().apply {

                            })
                        } ?: run { null })

                    }
                }
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                serverListener(ResponseState.InternetFailed, null)
            }
        })
    }
}
