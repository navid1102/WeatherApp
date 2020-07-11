package com.android.weathercore.apiCalls


import android.util.Log
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import okhttp3.ResponseBody
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


class Utils {
    companion object {
        fun <T> mapperTojson(tClass: Class<T>?, data: JsonObject?): T {

            val gson = GsonBuilder().serializeNulls().create()
            val gsonLog = GsonBuilder().setPrettyPrinting().create()
            Log.e("<<<<<<<<" + tClass?.simpleName + ">>>>>>>>", gsonLog.toJson(data))
            return gson.fromJson(gson.toJson(data), tClass)
        }

        fun <T> mapperTojson(tClass: Class<T>?, data: ResponseBody?): T {

            val gson = GsonBuilder().serializeNulls().create()
            val gsonLog = GsonBuilder().setPrettyPrinting().create()
            Log.e("<<<<<<<<" + tClass?.simpleName + ">>>>>>>>", gsonLog.toJson(data))
            return gson.fromJson(gson.toJson(data), tClass)
        }

        //////////
        fun separateText(txt: String): String {
            try {
                var originalString = txt
                val longval: Long?
                if (originalString.contains(",")) {
                    originalString = originalString.replace(",".toRegex(), "")
                }
                longval = java.lang.Long.parseLong(originalString)
                val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
                formatter.applyPattern("#,###,###,###")

                //setting text after format to EditText
                return formatter.format(longval)
            } catch (nfe: NumberFormatException) {
                return txt
            }

        }


    }




}