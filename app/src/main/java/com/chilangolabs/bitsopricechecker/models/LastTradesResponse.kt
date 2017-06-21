package com.chilangolabs.bitsopricechecker.models

import com.google.gson.annotations.SerializedName

data class LastTradesResponse(

        @field:SerializedName("payload")
        val payload: List<PayloadItem?>? = null,

        @field:SerializedName("success")
        val success: Boolean? = null
) {
    data class PayloadItem(

            @field:SerializedName("amount")
            val amount: String? = null,

            @field:SerializedName("price")
            val price: String? = null,

            @field:SerializedName("book")
            val book: String? = null,

            @field:SerializedName("created_at")
            val createdAt: String? = null,

            @field:SerializedName("maker_side")
            val makerSide: String? = null,

            @field:SerializedName("tid")
            val tid: Int? = null
    )
}