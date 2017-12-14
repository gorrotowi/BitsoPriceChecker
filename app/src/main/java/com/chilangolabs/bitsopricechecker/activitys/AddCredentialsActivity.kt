package com.chilangolabs.bitsopricechecker.activitys

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.chilangolabs.bitsopricechecker.R
import kotlinx.android.synthetic.main.activity_add_credentials.*

class AddCredentialsActivity : AppCompatActivity() {

    val qrCodeRequest = 4000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_credentials)

        btnAddCredentialsQr.setOnClickListener {
            startActivityForResult(Intent(this, QrReaderActivity::class.java), qrCodeRequest)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == qrCodeRequest)
            when (resultCode) {
                Activity.RESULT_OK -> {
                    Log.d("Data", "${data?.extras?.getString("key")}")
                    Log.d("Data", "${data?.extras?.getString("secret")}")
                }
                else -> Log.e("Canceled", "No data")
            }
    }

}
