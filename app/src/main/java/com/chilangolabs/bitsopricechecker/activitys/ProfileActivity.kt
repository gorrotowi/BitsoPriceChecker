package com.chilangolabs.bitsopricechecker.activitys

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.bitso.Bitso
import com.bitso.BitsoBalance
import com.bitso.BitsoBook
import com.chilangolabs.bitsopricechecker.BaseActivity
import com.chilangolabs.bitsopricechecker.R
import kotlinx.android.synthetic.main.activity_profile.*
import org.jetbrains.anko.doAsync

class ProfileActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        toolbar.title = " "
        toolbar.setStatusBarHeigh()
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val bitso = Bitso("hLrgRwMqmz", "80f39de30d04e27b0bcfb8299a8b76e7")


        doAsync {
            val data = bitso.getUserLedger(null)
            (0..(data.size - 1)).forEach {
                Log.e("dataledger", "${data[it].entryId}")
                Log.e("dataledger", "${data[it].details}")
                Log.e("dataledger", "${data[it].afterOperationBalances.size}")
                Log.e("dataledger", "${data[it].afterOperationBalances}")
                data[it].afterOperationBalances.map {
                    Log.e("dataBalance", "${it?.toString()}")
                }
                Log.e("dataledger", "---------------------------------------")
            }
        }

//        doAsync {
//            val data = bitso.getUserTrades()
//            (0..(data.size - 1)).forEach {
//                Log.e("trades", "${data[it].feesCurrency}")
//                Log.e("trades", "${data[it].price}")
//                Log.e("trades", "${data[it].feesAmount}")
//                Log.e("trades", "${data[it].feesCurrency}")
//            }
//        }

//        doAsync {
//            val data = bitso.userAccountBalance
//            showData(data)
//        }

        /*
        doAsync {
            val data = bitso.getUserFundings()
            Log.e("dataOrders", "${data.size}")
            (0..(data.size - 1)).forEach {
                Log.e("dataFundings", "${data[it].currency}")
                Log.e("dataFundings", "${data[it].amount}")
                Log.e("dataFundings", "${data[it].details}")
            }
        }
        */

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showData(await: BitsoBalance?): Unit {

    }
}
