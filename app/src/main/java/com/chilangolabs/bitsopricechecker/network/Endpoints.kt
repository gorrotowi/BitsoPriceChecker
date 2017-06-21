package com.chilangolabs.bitsopricechecker.network

import com.chilangolabs.bitsopricechecker.models.LastTradesResponse
import com.chilangolabs.bitsopricechecker.models.OrdersResponse
import com.chilangolabs.bitsopricechecker.models.TickerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by Gorro on 05/05/17.
 */
interface Endpoints {

    @GET("v3/ticker/")
    fun getTickers(): Call<TickerResponse>

    @GET("v3/order_book/")
    fun getBookOrder(@Query("book") book: String): Call<OrdersResponse>

    @GET("v3/trades/")
    fun getLastTrades(@Query("book") book: String, @Query("limit") limit: String = "10"): Call<LastTradesResponse>
}