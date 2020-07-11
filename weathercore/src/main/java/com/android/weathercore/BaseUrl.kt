package com.android.weathercore


enum class UrlType {
    Public,
    Loyalty,
    Local

}

object BaseUri {

//     https://api.openweathermap.org/data/2.5/onecall?lat=35.698238&lon=51.386062&
//     exclude=current,daily&appid=fb2e37587677305ad1785e7731a066b4

    var baseHost = "api.openweathermap.org"//baseUrl
    var baseUrl = "https://$baseHost"//baseUrl
    var identity = "https://api.openweathermap.org"//identity





}

open class Server{
    companion object {
        fun setServerType(urlType: UrlType) {
            when (urlType) {

                UrlType.Public -> {
                    BaseUri.baseHost = "api.openweathermap.org"
                    BaseUri.baseUrl = "https://${BaseUri.baseHost}"//baseUrl
                    BaseUri.identity = "https://api.openweathermap.org"

                }
            }

        }
    }

}

