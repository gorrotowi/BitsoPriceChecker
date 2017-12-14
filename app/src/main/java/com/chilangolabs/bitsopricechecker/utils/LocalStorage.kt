package com.chilangolabs.bitsopricechecker.utils

import android.content.Context
import android.content.SharedPreferences
import com.chilangolabs.bitsopricechecker.R

/**
 * Created by Gorro on 09/07/17.
 */
object LocalStorage {

    var sharedPreferences: SharedPreferences? = null

    fun initPreferences(context: Context) {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.apppreferences), Context.MODE_PRIVATE)
    }

    fun saveKey(key: String) = sharedPreferences?.edit()?.putString("key", key)?.apply()

    fun getKey() = sharedPreferences?.getString("key", "")

    fun saveSecret(secret: String) = sharedPreferences?.edit()?.putString("secret", secret)?.apply()

    fun getSecret() = sharedPreferences?.getString("secret", "")

}