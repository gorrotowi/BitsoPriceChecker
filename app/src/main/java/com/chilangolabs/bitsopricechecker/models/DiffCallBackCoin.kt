package com.chilangolabs.bitsopricechecker.models

import android.support.v7.util.DiffUtil

/**
 * @author Gorro
 * @since 15/06/17.
 */

class DiffCallBackCoin(private val oldItemCoinList: ArrayList<ItemCoin>?, private val newItemCoinList: List<ItemCoin>?) : DiffUtil.Callback() {

    override fun getOldListSize() = oldItemCoinList?.size ?: 0

    override fun getNewListSize() = newItemCoinList?.size ?: 0

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
            (oldItemCoinList?.get(oldItemPosition)?.value == newItemCoinList?.get(newItemPosition)?.value
                    && oldItemCoinList?.get(oldItemPosition)?.min == newItemCoinList?.get(newItemPosition)?.min
                    && oldItemCoinList?.get(oldItemPosition)?.max == newItemCoinList?.get(newItemPosition)?.max)

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val coinOld = oldItemCoinList?.get(oldItemPosition)
        val coinNew = newItemCoinList?.get(newItemPosition)
        return coinOld == coinNew
    }
}
