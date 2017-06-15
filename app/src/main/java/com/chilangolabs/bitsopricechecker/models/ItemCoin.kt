package com.chilangolabs.bitsopricechecker.models

import android.support.annotation.DrawableRes

/**
 * @author Gorro
 * @since 14/06/17
 */
data class ItemCoin(val coin: String? = "BTC",
                    val value: String? = "42000",
                    val currency: String? = "MXN",
                    val min: String? = "40200",
                    val max: String? = "42024",
                    @DrawableRes val bg: Int?)