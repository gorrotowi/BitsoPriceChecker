package com.chilangolabs.bitsopricechecker

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme
import com.chilangolabs.bitsopricechecker.adapters.ViewPagerAdapter
import com.chilangolabs.bitsopricechecker.fragments.BTCTickerFragment
import com.chilangolabs.bitsopricechecker.fragments.ETHTickerFragment
import com.chilangolabs.bitsopricechecker.network.Api
import com.chilangolabs.bitsopricechecker.utils.PriceSPreferences
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : BaseActivity() {

    val api = Api()

    val btcFragment = BTCTickerFragment()
    val ethFragment = ETHTickerFragment()

    val timer: Timer? = Timer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.setStatusBarHeigh()
        toolbar.title = ""
        setSupportActionBar(toolbar)

        mainLayout.startAnimationBackground()

        api.init(applicationContext)
        PriceSPreferences().init(applicationContext)

        val fragmentList = mutableListOf<Fragment>()
        fragmentList.add(btcFragment)
        fragmentList.add(ethFragment)

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, fragmentList)
        viewPagerIndicator.setupWithViewPager(viewPager)

        val handler: Handler = Handler()
        val doAsyncTask = object : TimerTask() {
            override fun run() {
                Log.e("TASK", "----------->>>>>>>>>>>>>>>>>>>>>>")
                handler.post {
                    Runnable {
                        Log.e("TASK", "------------------------------>>>>>>")
                        getTicker(useProgress = false)
                    }.run()
                }
            }
        }
        timer?.schedule(doAsyncTask, 0, 5000)

    }

    override fun onResume() {
        super.onResume()
//        getTicker()
        getChartsInfo()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.let {
            it.cancel()
            it.purge()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_refresh -> {
                getTicker()
                getChartsInfo()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getTicker(useProgress: Boolean = true) {
        val progressDialog = MaterialDialog.Builder(this)
                .progress(true, 0)
                .content("Update Data")
                .theme(Theme.LIGHT)
                .cancelable(false)
                .build()
        if (useProgress)
            progressDialog.show()

        api.getTicker(success = {
            if (useProgress)
                progressDialog.dismiss()
            val btcData = it.payload?.get(0)
            val ethData = it.payload?.get(1)

            if (btcData != null && ethData != null) {
                btcFragment.updateData(btcData)
                ethFragment.updateData(ethData)
            } else
                Toast.makeText(this, "Tuvimos problemas al recuperar algunos datos, intenta nuevamente mas tarde", Toast.LENGTH_SHORT).show()

        }, fail = {
            if (useProgress)
                progressDialog.dismiss()
        })

    }

    private fun getChartsInfo() {
        getChartBTCInfo()
        getChartETHInfo()
    }

    private fun getChartETHInfo() {
        api.getChartInfo(api.SYMBOL_BTC, success = {
            btcFragment.updateChart(it)
        }, fail = {
            Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
        })
    }

    private fun getChartBTCInfo() {
        api.getChartInfo(api.SYMBOL_ETH, success = {
            ethFragment.updateChart(it)
        }, fail = {
            Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
        })
    }

}
