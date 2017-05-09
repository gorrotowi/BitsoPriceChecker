package com.chilangolabs.bitsopricechecker.network

import com.chilangolabs.bitsopricechecker.models.ChartResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Gorro on 08/05/17.
 */
interface EndpointsChart {

    @GET("indices/global/history/{symbol}?period=monthly&format=json")
    fun getChart(@Path("symbol") symbol: String): Call<List<ChartResponse>>

}