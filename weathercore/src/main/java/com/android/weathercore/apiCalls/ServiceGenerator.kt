package com.android.weathercore.apiCalls

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import com.android.weathercore.customs.MyShared
import com.android.weathercore.BaseUri

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object ServiceGenerator {


    private val httpClient = OkHttpClient.Builder()
    private var retrofit: Retrofit? = null

    @SuppressLint("MissingPermission")
    fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }

    fun <S> createService(serviceClass: Class<S>, ms: MyShared): S {
        if (retrofit == null) {

            val builder = Retrofit.Builder()
                .baseUrl(BaseUri.identity)

            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY

            /*  val SIZE_OF_CACHE = (1000 * 1024 * 1024).toLong() // 10 MiB

              val cache = Cache(context.cacheDir, SIZE_OF_CACHE)*/

            httpClient.addInterceptor { chain ->
                val original = chain.request()

//                if (hasNetwork(context)?){
//                    val requestBuilder = original.newBuilder().removeHeader("Pragma").removeHeader("Cache-Control")
//                        .header("Cache-Control", "public, max-age=" + 60 * 60 * 1)
////                    .header("Content-Type", "application/json")
////                    .header("Authorization", "Bearer " + ms.token)
//                        .method(original.method(), original.body())
//
//                    val request = requestBuilder.build()
//
//
//                    val originalResponse = chain.proceed(request)
//                    originalResponse.newBuilder()
//                        .build()
//                }else {
//                val cacheControl = CacheControl.Builder()
//                    .maxStale(1, TimeUnit.DAYS)
//                    .build()
                var requestBuilder:Request.Builder? = null
                ms.token?.let {
                    requestBuilder  = original.newBuilder()
                        .header("Content-Type", "application/json")
                        .header("Authorization",  "Bearer " + it)


                        .method(original.method(), original.body())
                }?:run{
                    requestBuilder  = original.newBuilder()
                        .header("Content-Type", "application/json")



                        .method(original.method(), original.body())
                }



                val request = requestBuilder!!.build()


                val originalResponse = chain.proceed(request)
                originalResponse.newBuilder()
                    .build()


            }

            httpClient.addInterceptor(logging)
            //     httpClient.cache(cache)
            httpClient.connectTimeout(40, TimeUnit.SECONDS)
                .readTimeout(40, TimeUnit.SECONDS)
                .writeTimeout(40, TimeUnit.SECONDS)

            val client = httpClient.build()


            retrofit = builder.client(client).addConverterFactory(GsonConverterFactory.create()).build()


            return retrofit!!.create(serviceClass)


        } else {
            return retrofit!!.create(serviceClass)

        }
    }

}


