package com.chilangolabs.bitsopricechecker.network

import com.chilangolabs.bitsopricechecker.models.TickerResponse
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by Gorro on 05/05/17.
 */
interface Endpoints {

    @GET("v3/ticker/")
    fun getTickers(): Call<TickerResponse>

}