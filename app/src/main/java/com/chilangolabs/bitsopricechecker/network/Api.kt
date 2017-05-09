package com.chilangolabs.bitsopricechecker.network

import android.content.Context
import android.util.Log
import com.chilangolabs.bitsopricechecker.models.ChartResponse
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

    val SYMBOL_BTC = "BTCMXN"
    val SYMBOL_ETH = "ETHMXN"

    companion object factory {
        var retrofit: Retrofit? = null
        var retrofitChart: Retrofit? = null
        lateinit var endpoints: Endpoints
        lateinit var endpointsChart: EndpointsChart
    }

    fun getLogginInterceptor(): OkHttpClient? {
        val logginInterceptor = HttpLoggingInterceptor()
        logginInterceptor.level = HttpLoggingInterceptor.Level.NONE

        val builder = OkHttpClient.Builder()
        builder.interceptors().add(logginInterceptor)
        return builder.build()
    }

    fun getClient(): Retrofit? {

        when {
            Api.retrofit == null -> {
                retrofit = Retrofit.Builder()
                        .baseUrl("https://api.bitso.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(getLogginInterceptor())
                        .build()
            }
        }
        return retrofit
    }

    fun getClientChart(): Retrofit? {

        when {
            Api.retrofitChart == null -> {
                retrofitChart = Retrofit.Builder()
                        .baseUrl("https://apiv2.bitcoinaverage.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .client(getLogginInterceptor())
                        .build()
            }
        }
        return retrofitChart
    }

    fun init(ctx: Context) {
        endpoints = getClient()?.create(Endpoints::class.java)!!
        endpointsChart = getClientChart()?.create(EndpointsChart::class.java)!!

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

    fun getChartInfo(symbol: String, success: (response: List<ChartResponse>) -> Unit, fail: (error: Throwable) -> Unit) {
        val call: Call<List<ChartResponse>> = endpointsChart.getChart(symbol)
        call.enqueue(object : Callback<List<ChartResponse>> {

            override fun onResponse(call: Call<List<ChartResponse>>?, response: Response<List<ChartResponse>>?) {
                if (response?.code() == 200) {
                    Log.e("Chart $symbol", "OK!!!!")

                    success(response.body())
                } else {
                    fail(Throwable("Intenta nuevamente"))
                }
            }

            override fun onFailure(call: Call<List<ChartResponse>>?, t: Throwable?) {
                fail(t ?: Throwable("Error, intenta nuevamente"))
            }
        })
    }

}

