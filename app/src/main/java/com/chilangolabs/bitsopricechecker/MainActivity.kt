package com.chilangolabs.bitsopricechecker

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.chilangolabs.bitsopricechecker.activitys.AddCredentialsActivity
import com.chilangolabs.bitsopricechecker.activitys.ProfileActivity
import com.chilangolabs.bitsopricechecker.adapters.AdapterRcCoins
import com.chilangolabs.bitsopricechecker.models.ItemCoin
import com.chilangolabs.bitsopricechecker.models.SingletonResponseCoins
import com.chilangolabs.bitsopricechecker.network.Api
import com.chilangolabs.bitsopricechecker.utils.LocalStorage
import com.chilangolabs.bitsopricechecker.utils.openUrl
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crash.FirebaseCrash
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity
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
            R.drawable.bg_blue_round_corners,
            R.drawable.bg_pink_round_corners,
            R.drawable.bg_red_round_corners)
    var adapter = AdapterRcCoins(arrayListOf<ItemCoin>())

    val firebaseAnanalytics by lazy {
        FirebaseAnalytics.getInstance(this@MainActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = "Bitso® Ticker"
        setSupportActionBar(toolbar)

        api.init()
        LocalStorage.initPreferences(applicationContext)


        rcCoins.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rcCoins.setHasFixedSize(true)
        rcCoins.adapter = adapter

        swRefreshLayoutCoins.setOnRefreshListener {
            swRefreshLayoutCoins.isRefreshing = true
            val bundle = Bundle()
            bundle.putString(FirebaseAnalytics.Param.LOCATION, "ListCurrencys")
            firebaseAnanalytics.let {
                it?.logEvent(FirebaseAnalytics.Event.SEARCH, bundle)
            }
            getTickerData()
        }

        progressBarCoins.visibility = View.VISIBLE
        val handler: Handler = Handler()
        swRefreshLayoutCoins.isRefreshing = true
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_profile -> {
                when (LocalStorage.getKey()) {
                    "" -> showRegisterAlert()
                    else -> startActivity<ProfileActivity>()
                }
            }
        }
        return super.onOptionsItemSelected(item)
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
        rcCoins.adapter = null
    }

    private fun showRegisterAlert() {
        AlertDialog.Builder(this)
                .setTitle("Ingresa")
                .setMessage("Si ya tienes una cuenta en Bitso® puedes iniciar sesión, de lo contrario puedes registrarte")
                .setPositiveButton("Registrarme", { dialog, _ ->
                    dialog.dismiss()
                    openUrl(this, "https://bitso.com/?ref=4x4ibsnkhxn0vzs0hmboxiev")
                })
                .setNeutralButton("Iniciar Sesión", { dialog, _ ->
                    startActivity<AddCredentialsActivity>()
                    dialog.dismiss()
                })
                .create()
                .show()

    }

    private fun getTickerData() {
        api.getTicker(success = {
            swRefreshLayoutCoins.isRefreshing = false
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
                                    payloadItem?.ask,
                                    payloadItem?.bid,
                                    bgCoins[index]
                            ))
                }
                SingletonResponseCoins.coinsChange(dataCoins)
                adapter.updateCoinListItems(dataCoins)
            }
        }, fail = {
            swRefreshLayoutCoins.isRefreshing = false
            progressBarCoins.visibility = View.GONE
            FirebaseCrash.logcat(Log.WARN, "DATA", "No Data")
            FirebaseCrash.log("The adapter have ${adapter.itemCount} and the localData have ${dataCoins.size}")
            FirebaseCrash.report(it)
            if (adapter.itemCount <= 0 && dataCoins.isEmpty()) {
                txtErrorNoData.visibility = View.GONE
            } else {
                txtErrorNoData.visibility = View.VISIBLE
            }
        })
    }


}
