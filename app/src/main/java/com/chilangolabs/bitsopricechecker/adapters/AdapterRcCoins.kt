package com.chilangolabs.bitsopricechecker.adapters

import android.app.Activity
import android.content.Intent
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.util.Pair
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chilangolabs.bitsopricechecker.R
import com.chilangolabs.bitsopricechecker.activitys.CoinDetailActivity
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

            holder?.bindView(it)

            holder?.itemView?.setOnClickListener {
                val context = holder.itemView?.context
                val intent = Intent(context, CoinDetailActivity::class.java)
                intent.putExtra(context?.getString(R.string.viewbg_transition_name), data[position].bg)
                intent.putExtra(context?.getString(R.string.coin_transition_name), data[position].coin)
                intent.putExtra(context?.getString(R.string.currency_transition_name), data[position].currency)
                intent.putExtra(context?.getString(R.string.price_transition_name), data[position].value)
                intent.putExtra(context?.getString(R.string.low_price), data[position].min)
                intent.putExtra(context?.getString(R.string.max_price), data[position].max)
                intent.putExtra(context?.getString(R.string.ask_price), data[position].ask)
                intent.putExtra(context?.getString(R.string.bid_price), data[position].bid)
                val p1: Pair<View, String> = Pair.create(holder.itemView?.cardContainer, context?.getString(R.string.viewbg_transition_name))
                val p2: Pair<View, String> = Pair.create(holder.itemView?.txtItemCryptoName, context?.getString(R.string.coin_transition_name))
                val p3: Pair<View, String> = Pair.create(holder.itemView?.txtItemCurrency, context?.getString(R.string.currency_transition_name))
                val p4: Pair<View, String> = Pair.create(holder.itemView?.txtItemPrice, context?.getString(R.string.price_transition_name))
                val p5: Pair<View, String> = Pair.create(holder.itemView?.cardPriceContainer, context?.getString(R.string.card_chart_transition_name))
                val options: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(context as Activity?, p1, p2, p3, p4, p5)
                holder.itemView?.context?.startActivity(intent, options.toBundle())
            }

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

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindView(itemcoin: ItemCoin?) {
            itemcoin?.let {
                itemView?.cardContainer?.setBackgroundResource(it.bg ?: R.drawable.bg_pink_round_corners)
                itemView?.txtItemCryptoName?.text = it.coin
                itemView?.txtItemMin?.text = it.min
                itemView?.txtItemMax?.text = it.max
                itemView?.txtItemCurrency?.text = it.currency
                itemView?.txtItemPrice?.text = it.value
            }
        }
    }

}