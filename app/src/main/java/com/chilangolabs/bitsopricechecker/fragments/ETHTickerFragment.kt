package com.chilangolabs.bitsopricechecker.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chilangolabs.bitsopricechecker.R
import com.chilangolabs.bitsopricechecker.models.PayloadItem
import kotlinx.android.synthetic.main.container_ask_bid.view.*
import kotlinx.android.synthetic.main.content_low_and_high.view.*
import kotlinx.android.synthetic.main.fragment_ethticker.*

class ETHTickerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_ethticker, container, false)

    }

    fun updateData(data: PayloadItem) {
        txtEthLast.text = getString(R.string.template_format_money, data.last)
        containerETHLowHigh.txtLow.text = getString(R.string.template_format_money, data.low)
        containerETHLowHigh.txtHigh.text = getString(R.string.template_format_money, data.high)
        containerEthAskBid.txtBid.text = getString(R.string.template_format_money, data.bid)
        containerEthAskBid.txtAsk.text = getString(R.string.template_format_money, data.ask)
    }

}
