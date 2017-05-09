package com.chilangolabs.bitsopricechecker.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ChartResponse(

        @field:SerializedName("average")
        @field:Expose
        val average: Double? = null,

        @field:SerializedName("time")
        @field:Expose
        val time: String? = null,

        @field:SerializedName("high")
        @field:Expose
        val high: String? = null,

        @field:SerializedName("low")
        @field:Expose
        val low: String? = null
)