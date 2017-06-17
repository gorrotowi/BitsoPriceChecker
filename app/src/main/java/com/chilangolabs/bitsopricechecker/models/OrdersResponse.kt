package com.chilangolabs.bitsopricechecker.models

import com.google.gson.annotations.SerializedName

data class OrdersResponse(

        @field:SerializedName("payload")
        val payload: Payload? = null,

        @field:SerializedName("success")
        val success: Boolean? = null
) {
    data class Payload(

            @field:SerializedName("sequence")
            val sequence: String? = null,

            @field:SerializedName("updated_at")
            val updatedAt: String? = null,

            @field:SerializedName("asks")
            val asks: List<AsksBidsItem?>? = null,

            @field:SerializedName("bids")
            val bids: List<AsksBidsItem?>? = null
    )

    data class AsksBidsItem(

            @field:SerializedName("amount")
            val amount: String? = null,

            @field:SerializedName("price")
            val price: String? = null,

            @field:SerializedName("book")
            val book: String? = null
    )

}