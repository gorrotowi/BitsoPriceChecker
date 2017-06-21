package com.chilangolabs.bitsopricechecker.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chilangolabs.bitsopricechecker.R
import com.chilangolabs.bitsopricechecker.models.ItemLastTrades
import com.gorrotowi.simplerobototextview.RobotoTextView

/**
 * @author Gorro
 * @since 20/06/17.
 */
class AdapterLastTrades(val data: ArrayList<ItemLastTrades>?) : RecyclerView.Adapter<AdapterLastTrades.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = ViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_last_trades, parent, false))

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        data?.get(position).let {
            holder?.txtPrice?.text = it?.price
            holder?.txtAmount?.text = it?.amount
            holder?.txtType?.text = it?.type

            when (it?.type) {
                "buy" -> print("buy")
                "sell" -> print("sell")
            }
        }
    }

    override fun getItemCount() = data?.size ?: 0

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtPrice = itemView.findViewById(R.id.txtItemLastTradePrice) as RobotoTextView
        val txtAmount = itemView.findViewById(R.id.txtItemLastTradeAmount) as RobotoTextView
        val txtType = itemView.findViewById(R.id.txtItemLastTradeType) as RobotoTextView
    }
}

