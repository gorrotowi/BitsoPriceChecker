package com.chilangolabs.bitsopricechecker.network

import android.content.Context
import android.util.Log
import com.chilangolabs.bitsopricechecker.models.TickerResponse
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Gorro on 05/05/17.
 */
class Api {
    companion object factory {
        var retrofit: Retrofit? = null
        lateinit var endpoints: Endpoints
    }

    fun getClient(): Retrofit? {


        val logginInterceptor = HttpLoggingInterceptor()
        logginInterceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
        builder.interceptors().add(logginInterceptor)
        val client = builder.build()

        if (Api.retrofit == null) {
            retrofit = Retrofit.Builder()
                    .baseUrl("https://api.bitso.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(client)
                    .build()
        }
        return retrofit
    }

    fun init(ctx: Context) {
        endpoints = getClient()?.create(Endpoints::class.java)!!
    }

    fun getTicker(success: (response: TickerResponse) -> Unit, fail: (error: Throwable) -> Unit) {
        val call: Call<TickerResponse> = endpoints.getTickers()
        call.enqueue(object : Callback<TickerResponse> {

            override fun onResponse(call: Call<TickerResponse>?, response: Response<TickerResponse>?) {
                if (response?.code() == 200) {
                    success(response.body())
                } else {
                    fail(Throwable("Intenta nuevamente"))
                }
            }

            override fun onFailure(call: Call<TickerResponse>?, t: Throwable?) {
                fail(t ?: Throwable("Error, intenta nuevamente"))
            }

        })
    }

}

