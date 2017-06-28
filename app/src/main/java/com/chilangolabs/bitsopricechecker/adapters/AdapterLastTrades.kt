package com.chilangolabs.bitsopricechecker.adapters

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chilangolabs.bitsopricechecker.R
import com.chilangolabs.bitsopricechecker.models.ItemLastTrades
import kotlinx.android.synthetic.main.item_last_trades.view.*

/**
 * @author Gorro
 * @since 20/06/17.
 */
class AdapterLastTrades(val data: ArrayList<ItemLastTrades>?) : RecyclerView.Adapter<AdapterLastTrades.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_last_trades, parent, false))

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        data?.get(position).let {
            holder?.bindView(it)
        }
    }

    override fun getItemCount() = data?.size ?: 0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(itemLastTrade: ItemLastTrades?) {
            itemView?.txtItemLastTradeAmount?.text = itemLastTrade?.amount
            itemView?.txtItemLastTradePrice?.text = itemLastTrade?.price
            itemView?.txtItemLastTradeType?.text = itemLastTrade?.type

            when (itemLastTrade?.type) {
                "buy" -> itemView?.txtItemLastTradeType?.setTextColor(Color.parseColor("#c62b5c"))
                "sell" -> itemView?.txtItemLastTradeType?.setTextColor(Color.parseColor("#3ed13e"))
                else -> print("no color")
            }
        }
    }
}

