package com.chilangolabs.bitsopricechecker.activitys

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.chilangolabs.bitsopricechecker.BaseActivity
import com.google.zxing.BarcodeFormat
import com.google.zxing.Result
import me.dm7.barcodescanner.zxing.ZXingScannerView
import org.jetbrains.anko.toast

class QrReaderActivity : BaseActivity(), ZXingScannerView.ResultHandler {

    val scannerView by lazy {
        ZXingScannerView(this@QrReaderActivity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(scannerView)
    }

    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this)
        scannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    override fun handleResult(result: Result?) {
        when (result?.barcodeFormat) {
            BarcodeFormat.QR_CODE -> processResult(result.text ?: "")
            else -> {
                returnFalse()
                toast("Incorrect barcode format")
            }
        }
    }

    private fun processResult(qrtext: String) {
        val keys = qrtext.split(":")
        if (keys.size > 1) {
            val intent = Intent()
            intent.putExtra("key", keys[0])
            intent.putExtra("secret", keys[1])
            setResult(Activity.RESULT_OK, intent)
        } else {
            returnFalse()
            toast("The format is incorrect")
        }
    }

    private fun returnFalse() {
        setResult(Activity.RESULT_CANCELED)
    }
}
