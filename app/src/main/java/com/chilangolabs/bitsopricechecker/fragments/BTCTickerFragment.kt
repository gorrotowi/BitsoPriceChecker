package com.chilangolabs.bitsopricechecker.fragments


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chilangolabs.bitsopricechecker.R
import com.chilangolabs.bitsopricechecker.activitys.CoinDetailActivity
import com.chilangolabs.bitsopricechecker.adapters.SparkChartAdapter
import com.chilangolabs.bitsopricechecker.models.ChartResponse
import com.chilangolabs.bitsopricechecker.models.TickerResponse
import com.chilangolabs.bitsopricechecker.utils.PriceSPreferences
import com.chilangolabs.bitsopricechecker.utils.getDateF
import com.chilangolabs.bitsopricechecker.utils.getDateFCheck
import kotlinx.android.synthetic.main.container_ask_bid.view.*
import kotlinx.android.synthetic.main.container_chart.view.*
import kotlinx.android.synthetic.main.content_low_and_high.view.*
import kotlinx.android.synthetic.main.fragment_btcticker.*

class BTCTickerFragment : Fragment() {

    val spreferences = PriceSPreferences()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_btcticker, container, false)
    }

    override fun onResume() {
        super.onResume()
        txtBTCLast.setOnClickListener {
            startActivity(Intent(activity, CoinDetailActivity::class.java))
        }
        txtBTCLast.text = spreferences.getBTCLast()
        txtBTCLastDate.text = spreferences.getLastDate()
        containerBTCLowHigh.txtLow.text = spreferences.getBTCLow()
        containerBTCLowHigh.txtHigh.text = spreferences.getBTCHigh()
        containerBtcAskBid.txtBid.text = spreferences.getBTCBid()
        containerBtcAskBid.txtAsk.text = spreferences.getBTCAsk()
    }

    fun updateData(data: TickerResponse.PayloadItem) {
        txtBTCLast.text = getString(R.string.template_format_money, data.last)
        containerBTCLowHigh.txtLow.text = getString(R.string.template_format_money, data.low)
        containerBTCLowHigh.txtHigh.text = getString(R.string.template_format_money, data.high)
        containerBtcAskBid.txtBid.text = getString(R.string.template_format_money, data.bid)
        containerBtcAskBid.txtAsk.text = getString(R.string.template_format_money, data.ask)

        spreferences.saveBTCLast(txtBTCLast.text.toString())
        spreferences.saveBTCLow(containerBTCLowHigh.txtLow.text.toString())
        spreferences.saveBTCHigh(containerBTCLowHigh.txtHigh.text.toString())
        spreferences.saveBTCBid(containerBtcAskBid.txtBid.text.toString())
        spreferences.saveBTCAsk(containerBtcAskBid.txtAsk.text.toString())
        spreferences.saveLastDate(data.createdAt?.getDateFCheck().toString())

    }

    fun updateChart(data: List<ChartResponse>) {
        Log.e("DATACHART", "$data")
        val chartData: FloatArray = kotlin.FloatArray(data.size)
        for (i in 0..(data.size - 1)) {
            chartData[i] = data[i].average?.toFloat() ?: 0.toFloat()
        }
        Log.e("DATE", data[0].time?.getDateF())
        containerBTCChart.txtLastDate.text = data[0].time?.getDateF()
        containerBTCChart.txtMiddleDate.text = data[data.size / 2].time?.getDateF()
        containerBTCChart.txtOldDate.text = data[data.size - 1].time?.getDateF()
        val adapterChart = SparkChartAdapter(chartData.reversedArray())
        containerBTCChart.sparkChart.adapter = adapterChart
        containerBTCChart.sparkChart.setScrubListener { obj, pos ->
            obj?.let {
                containerBTCChart.txtChartInfoSpark.text = getString(R.string.template_format_money, it)
                containerBTCChart.txtChartDateSpark.text = data.reversed()[pos].time?.getDateF()
            }
        }
    }

}
