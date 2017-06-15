package com.chilangolabs.bitsopricechecker.adapters

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chilangolabs.bitsopricechecker.R
import com.chilangolabs.bitsopricechecker.models.DiffCallBackCoin
import com.chilangolabs.bitsopricechecker.models.ItemCoin
import kotlinx.android.synthetic.main.item_coin.view.*

/**
 * @author Gorro
 * @since 14/06/17.
 */
class AdapterRcCoins(val data: ArrayList<ItemCoin>?) : RecyclerView.Adapter<AdapterRcCoins.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_coin, parent, false))

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        data?.get(position)?.let {
            holder?.itemView?.cardContainer?.setBackgroundResource(it.bg ?: R.drawable.bg_pink_round_corners)
            holder?.itemView?.txtItemCryptoName?.text = it.coin
            holder?.itemView?.txtItemMin?.text = it.min
            holder?.itemView?.txtItemMax?.text = it.max
            holder?.itemView?.txtItemCurrency?.text = it.currency
            holder?.itemView?.txtItemPrice?.text = it.value
        }
    }

    override fun getItemCount() = data?.size ?: 0

    fun updateCoinListItems(coins: List<ItemCoin>?) {
        val diffCallback = DiffCallBackCoin(data, coins)
        val diffUtilResult = DiffUtil.calculateDiff(diffCallback)
        data?.clear()
        coins?.let { data?.addAll(it) }
        diffUtilResult.dispatchUpdatesTo(this)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}