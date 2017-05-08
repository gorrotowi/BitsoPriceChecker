package com.chilangolabs.bitsopricechecker

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.LinearLayout

/**
 * Created by Gorro on 07/05/17.
 */

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun ViewGroup.setStatusBarHeigh() {
        val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
        val lparams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        if (resourceId > 0) {
            lparams.setMargins(0, resources.getDimensionPixelSize(resourceId), 0, 0)
            this.layoutParams = lparams
        }
    }

}
