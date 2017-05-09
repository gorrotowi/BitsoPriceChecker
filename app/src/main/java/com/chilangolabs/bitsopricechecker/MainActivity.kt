package com.chilangolabs.bitsopricechecker

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.Theme
import com.chilangolabs.bitsopricechecker.adapters.ViewPagerAdapter
import com.chilangolabs.bitsopricechecker.fragments.BTCTickerFragment
import com.chilangolabs.bitsopricechecker.fragments.ETHTickerFragment
import com.chilangolabs.bitsopricechecker.network.Api
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    val api = Api()

    val btcFragment = BTCTickerFragment()
    val ethFragment = ETHTickerFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.setStatusBarHeigh()
        toolbar.title = ""
        setSupportActionBar(toolbar)

        mainLayout.startAnimationBackground()

        api.init(applicationContext)

        val fragmentList = mutableListOf<Fragment>()
        fragmentList.add(btcFragment)
        fragmentList.add(ethFragment)

        viewPager.adapter = ViewPagerAdapter(supportFragmentManager, fragmentList)
        viewPagerIndicator.setupWithViewPager(viewPager)

    }

    override fun onResume() {
        super.onResume()
        getTicker()
        getChartsInfo()
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
