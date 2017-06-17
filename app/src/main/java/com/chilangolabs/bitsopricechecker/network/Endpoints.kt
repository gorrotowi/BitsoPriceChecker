package com.chilangolabs.bitsopricechecker.network

import com.chilangolabs.bitsopricechecker.models.OrdersResponse
import com.chilangolabs.bitsopricechecker.models.TickerResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Created by Gorro on 05/05/17.
 */
interface Endpoints {

    @GET("v3/ticker/")
    fun getTickers(): Call<TickerResponse>

    @GET("https://api.bitso.com/v3/order_book/?book={book}")
    fun getBookOrder(@Path("book") book: String): Call<OrdersResponse>
}