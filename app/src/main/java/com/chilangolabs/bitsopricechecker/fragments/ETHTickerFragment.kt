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

    fun updateChart(data: List<ChartResponse>) {
        Log.e("DATACHART", "$data")
        val chartData: FloatArray = kotlin.FloatArray(data.size)
        for (i in 0..(data.size - 1)) {
            chartData[i] = data[i].average?.toFloat() ?: 0.toFloat()
        }

        Log.i("-----", "------")

        Log.e("DATE", data[0].time?.getDateF())

        containerETHChart.txtLastDate.text = data[0].time?.getDateF()
        containerETHChart.txtMiddleDate.text = data[data.size / 2].time?.getDateF()
        containerETHChart.txtOldDate.text = data[data.size - 1].time?.getDateF()

        val adapterChart = SparkChartAdapter(chartData.reversedArray())
        containerETHChart.sparkChart.adapter = adapterChart
        containerETHChart.sparkChart.setScrubListener { obj, pos ->
            obj?.let {
                containerETHChart.txtChartInfoSpark.text = getString(R.string.template_format_money, it)
                containerETHChart.txtChartDateSpark.text = data.reversed()[pos].time?.getDateF()
            }
        }
    }

}
