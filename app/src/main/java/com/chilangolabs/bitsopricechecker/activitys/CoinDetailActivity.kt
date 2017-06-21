package com.chilangolabs.bitsopricechecker.activitys

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.MenuItem
import android.view.View
import com.chilangolabs.bitsopricechecker.BaseActivity
import com.chilangolabs.bitsopricechecker.R
import com.chilangolabs.bitsopricechecker.adapters.AdapterLastTrades
import com.chilangolabs.bitsopricechecker.adapters.SparkChartAdapter
import com.chilangolabs.bitsopricechecker.models.ChartResponse
import com.chilangolabs.bitsopricechecker.models.ItemLastTrades
import com.chilangolabs.bitsopricechecker.models.LastTradesResponse
import com.chilangolabs.bitsopricechecker.network.Api
import com.chilangolabs.bitsopricechecker.utils.getDateF
import kotlinx.android.synthetic.main.coin_content.*
import kotlinx.android.synthetic.main.fragment_coin.*

class CoinDetailActivity : BaseActivity() {

    val api = Api()
    var currency = ""
    lateinit var adapter: AdapterLastTrades

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_coin)

        toolbar.title = ""
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val linearly = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcLastTrades.layoutManager = linearly
        rcLastTrades.setHasFixedSize(true)

        intent?.extras?.let {
            val bg = it.getInt(getString(R.string.viewbg_transition_name), R.drawable.bg_pink_round_corners)
            val coin = it.getString(getString(R.string.coin_transition_name), "BTC")
            currency = it.getString(getString(R.string.currency_transition_name), "MXN")
            val price = it.getString(getString(R.string.price_transition_name), "4200")
            val ask = it.getString(getString(R.string.ask_price), "4200")
            val bid = it.getString(getString(R.string.bid_price), "4200")

            viewBgGradient.setBackgroundResource(bg)
            txtCoinCurrencyCrypto.text = coin
            txtCoinCurrency.text = currency
            txtCoinValue.text = price
            txtCoinMin.text = "Ask\n$ask"
            txtCoinMax.text = "Bid\n$bid"

            getChartInfo("${coin.toUpperCase()}${currency.toUpperCase()}")
            getLastOrders("${coin.toLowerCase()}_${currency.toLowerCase()}")
//            getBooksOrders("${coin.toLowerCase()}_${currency.toLowerCase()}")
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getBooksOrders(book: String): Unit {
        api.getBooksOrder(book, success = {
            //TODO fill the recyclerview
            Log.e("BOOKS", "$it")
        }, fail = {
            //TODO show error message if you haven't data
            Log.e("BOOKS ERR", it.message)
        })
    }

    private fun getLastOrders(book: String) {
        api.getLastOrdersBook(book, success = {
            fillRcLastTrades(it.payload)
        }, fail = {

        })
    }

    private fun fillRcLastTrades(payload: List<LastTradesResponse.PayloadItem?>?) {
        val arrayTrades = arrayListOf(ItemLastTrades("Price", "Amount", "Type"))
        payload?.let {
            it.map {
                arrayTrades.add(ItemLastTrades(it?.price, it?.amount, it?.makerSide))
            }
            adapter = AdapterLastTrades(arrayTrades)
            rcLastTrades.adapter = adapter
        }
    }

    private fun getChartInfo(symbol: String): Unit {
        progressBarCoinChart.visibility = View.VISIBLE
        txtProgressBarCoinChart.visibility = View.VISIBLE
        txtProgressBarCoinChart.text = getString(R.string.loading_message)

        api.getChartInfo(symbol, success = {
            updateChart(it)
            progressBarCoinChart.visibility = View.GONE
            txtProgressBarCoinChart.visibility = View.GONE
        }, fail = {
            txtProgressBarCoinChart.text = getString(R.string.reload_chart)
            txtProgressBarCoinChart.setOnClickListener {
                getChartInfo(symbol)
            }
        })
    }

    fun updateChart(data: List<ChartResponse>) {
        val chartData: FloatArray = kotlin.FloatArray(data.size)
        for (i in 0..(data.size - 1)) {
            chartData[i] = data[i].average?.toFloat() ?: 0.toFloat()
        }

        txtCoinChartValue.text = "${currency.toUpperCase()}${data[0].average}"
        txtCoinChartValueDate.text = data[0].time?.getDateF()

        txtCoinLastDate.text = data[0].time?.getDateF()
        txtCoinMiddleDate.text = data[data.size / 2].time?.getDateF()
        txtCoinOldDate.text = data[data.size - 1].time?.getDateF()

        val adapterChart = SparkChartAdapter(chartData.reversedArray())
        sparkChartCoin.adapter = adapterChart
        sparkChartCoin.setScrubListener { obj, pos ->
            obj?.let {
                txtCoinChartValue.text = "${currency.toUpperCase()}$it"
                txtCoinChartValueDate.text = data.reversed()[pos].time?.getDateF()
            }
        }
    }


}
