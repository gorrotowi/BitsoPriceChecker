package com.chilangolabs.bitsopricechecker.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by Gorro on 09/05/17.
 */
class PriceSPreferences {

    val PREFERENCES = "com.chilangolabs.preferences"

    companion object factory {
        var sharedPreferences: SharedPreferences? = null
    }

    fun init(ctx: Context) {
        sharedPreferences = ctx.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    }

    fun saveLastDate(value: String) {
        sharedPreferences?.edit()?.putString("date", value)?.apply()
    }

    fun getLastDate() = sharedPreferences?.getString("date", "20/03/2017")

    //BTC Price

    fun saveBTCLast(value: String) {
        sharedPreferences?.edit()?.putString("btcLast", value)?.apply()
    }

    fun getBTCLast() = sharedPreferences?.getString("btcLast", "31000")

    fun saveBTCLow(value: String) {
        sharedPreferences?.edit()?.putString("btcLow", value)?.apply()
    }

    fun getBTCLow() = sharedPreferences?.getString("btcLow", "31000")

    fun saveBTCHigh(value: String) {
        sharedPreferences?.edit()?.putString("btcHight", value)?.apply()
    }

    fun getBTCHigh() = sharedPreferences?.getString("btcHigh", "31000")

    fun saveBTCBid(value: String) {
        sharedPreferences?.edit()?.putString("btcBid", value)?.apply()
    }

    fun getBTCBid() = sharedPreferences?.getString("btcBid", "31000")

    fun saveBTCAsk(value: String) {
        sharedPreferences?.edit()?.putString("btcAsk", value)?.apply()
    }

    fun getBTCAsk() = sharedPreferences?.getString("btcAsk", "31000")

    //ETH Price

    fun saveETHLast(value: String) {
        sharedPreferences?.edit()?.putString("ethLast", value)?.apply()
    }

    fun getETHLast() = sharedPreferences?.getString("ethLast", "1000")

    fun saveETHLow(value: String) {
        sharedPreferences?.edit()?.putString("ethLow", value)?.apply()
    }

    fun getETHLow() = sharedPreferences?.getString("ethLow", "1000")

    fun saveETHHigh(value: String) {
        sharedPreferences?.edit()?.putString("ethHight", value)?.apply()
    }

    fun getETHHigh() = sharedPreferences?.getString("ethHigh", "1000")

    fun saveETHBid(value: String) {
        sharedPreferences?.edit()?.putString("ethBid", value)?.apply()
    }

    fun getETHBid() = sharedPreferences?.getString("ethBid", "1000")

    fun saveETHAsk(value: String) {
        sharedPreferences?.edit()?.putString("ethAsk", value)?.apply()
    }

    fun getETHAsk() = sharedPreferences?.getString("ethAsk", "1000")

}