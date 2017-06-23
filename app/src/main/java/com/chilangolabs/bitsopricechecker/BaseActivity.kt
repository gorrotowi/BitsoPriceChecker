package com.chilangolabs.bitsopricechecker

import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.app.AppCompatActivity
import android.view.ViewGroup
import android.widget.LinearLayout

/**
 * @author Gorro
 * @since 07/05/17.
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

    fun ViewGroup.startAnimationBackground() {
        val animationDrawable = this.background as AnimationDrawable
        animationDrawable.setEnterFadeDuration(2500)
        animationDrawable.setExitFadeDuration(5000)
        animationDrawable.start()
    }

    fun AppBarLayout.hideShowTitle(collapsingToolbar: CollapsingToolbarLayout, title: String) {
        var isShow = false
        var scrollRange = -1
        this.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (scrollRange == -1) {
                scrollRange = appBarLayout.totalScrollRange
            }
            if (scrollRange + verticalOffset == 0) {
                collapsingToolbar.title = title
                isShow = true
            } else if (isShow) {
                collapsingToolbar.title = " "
                isShow = false
            }
        }
    }

}
