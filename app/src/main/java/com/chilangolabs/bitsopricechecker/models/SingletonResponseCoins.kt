package com.chilangolabs.bitsopricechecker.models

/**
 * @author Gorro
 * @since 22/06/17.
 */

object SingletonResponseCoins {
    private var listener: OnValueChange? = null

    fun coinsChange(coinsLast: List<ItemCoin>) {
        listener?.OnCoinChangeListener(coinsLast)
    }

    fun onCoinsChanged(changed: (List<ItemCoin>?) -> Unit) {
        listener = object : OnValueChange {
            override fun OnCoinChangeListener(coins: List<ItemCoin>?) {
                changed(coins)
            }

        }
    }


}

interface OnValueChange {
    fun OnCoinChangeListener(coins: List<ItemCoin>?)
}