package com.chilangolabs.bitsopricechecker

import android.os.Bundle
import android.os.Handler

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.chilangolabs.bitsopricechecker.adapters.AdapterRcCoins
import com.chilangolabs.bitsopricechecker.models.ItemCoin
import com.chilangolabs.bitsopricechecker.network.Api
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

/**
 * @author Gorro
 * @since 07/05/17.
 */

class MainActivity : BaseActivity() {

    val api = Api()
    val timer: Timer? = Timer()
    val dataCoins = arrayListOf<ItemCoin>()
    val bgCoins = arrayListOf(R.drawable.bg_yellow_round_corners,
            R.drawable.bg_gray_round_corners,
            R.drawable.bg_blue_round_corners,
            R.drawable.bg_pink_round_corners,
            R.drawable.bg_red_round_corners,
            R.drawable.bg_yellow_round_corners,
            R.drawable.bg_gray_round_corners,
            R.drawable.bg_blue_round_corners)
    var adapter = AdapterRcCoins(arrayListOf<ItemCoin>())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        toolbar.setStatusBarHeigh()
        toolbar.title = "Bitso® Ticker"
        setSupportActionBar(toolbar)

        api.init(applicationContext)

        rcCoins.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) as RecyclerView.LayoutManager
        rcCoins.setHasFixedSize(true)
        rcCoins.adapter = adapter

        progressBarCoins.visibility = View.VISIBLE
        val handler: Handler = Handler()
        val doAsyncTask = object : TimerTask() {
            override fun run() {
                handler.post {
                    Runnable {
                        getTickerData()
                    }.run()
                }
            }
        }
        timer?.schedule(doAsyncTask, 0, 5000)

    }

    override fun onResume() {
        super.onResume()
        getTickerData()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.let {
            it.cancel()
            it.purge()
        }
    }

    private fun getTickerData() {
        api.getTicker(success = {
            progressBarCoins.visibility = View.GONE
            txtErrorNoData.visibility = View.GONE
            it.payload?.let {
                dataCoins.clear()
                it.mapIndexed { index, payloadItem ->
                    val splitBook = payloadItem?.book?.split("_")
                    dataCoins.add(
                            ItemCoin(splitBook?.get(0)?.toUpperCase(),
                                    payloadItem?.last,
                                    splitBook?.get(1)?.toUpperCase(),
                                    payloadItem?.low,
                                    payloadItem?.high,
                                    bgCoins[index]
                            ))
                }
                adapter.updateCoinListItems(dataCoins)
            }
        }, fail = {
            progressBarCoins.visibility = View.GONE
            if (adapter.itemCount <= 0) {
                txtErrorNoData.visibility = View.GONE
            } else {
                txtErrorNoData.visibility = View.VISIBLE
            }
        })
    }


}
