package com.chilangolabs.bitsopricechecker.fragments


import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chilangolabs.bitsopricechecker.R
import com.chilangolabs.bitsopricechecker.adapters.SparkChartAdapter
import com.chilangolabs.bitsopricechecker.models.ChartResponse
import com.chilangolabs.bitsopricechecker.models.PayloadItem
import com.chilangolabs.bitsopricechecker.utils.getDateF
import kotlinx.android.synthetic.main.container_ask_bid.view.*
import kotlinx.android.synthetic.main.container_chart.view.*
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
