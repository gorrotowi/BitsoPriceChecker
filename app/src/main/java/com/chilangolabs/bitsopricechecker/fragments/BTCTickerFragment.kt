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
import kotlinx.android.synthetic.main.fragment_btcticker.*

class BTCTickerFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_btcticker, container, false)
    }

    fun updateData(data: PayloadItem) {
        txtBTCLast.text = getString(R.string.template_format_money, data.last)
        containerBTCLowHigh.txtLow.text = getString(R.string.template_format_money, data.low)
        containerBTCLowHigh.txtHigh.text = getString(R.string.template_format_money, data.high)
        containerBtcAskBid.txtBid.text = getString(R.string.template_format_money, data.bid)
        containerBtcAskBid.txtAsk.text = getString(R.string.template_format_money, data.ask)
    }

}
