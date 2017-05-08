package com.chilangolabs.bitsopricechecker.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TickerResponse(
        @field:SerializedName("payload")
        @field:Expose
        val payload: List<PayloadItem?>? = null,

        @field:SerializedName("success")
        @field:Expose
        val success: Boolean? = null

)

data class PayloadItem(
        @field:SerializedName("volume")
        @field:Expose
        val volume: String? = null,

        @field:SerializedName("high")
        @field:Expose
        val high: String? = null,

        @field:SerializedName("last")
        @field:Expose
        val last: String? = null,

        @field:SerializedName("low")
        @field:Expose
        val low: String? = null,

        @field:SerializedName("book")
        @field:Expose
        val book: String? = null,

        @field:SerializedName("vwap")
        @field:Expose
        val vwap: String? = null,

        @field:SerializedName("ask")
        @field:Expose
        val ask: String? = null,

        @field:SerializedName("created_at")
        @field:Expose
        val createdAt: String? = null,

        @field:SerializedName("bid")
        @field:Expose
        val bid: String? = null
)
