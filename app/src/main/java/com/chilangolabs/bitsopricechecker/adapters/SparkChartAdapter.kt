package com.chilangolabs.bitsopricechecker.adapters

import com.robinhood.spark.SparkAdapter

/**
 * Created by Gorro on 08/05/17.
 */

class SparkChartAdapter(private val yData: FloatArray) : SparkAdapter() {

    override fun getCount(): Int {
        return yData.size
    }

    override fun getItem(index: Int): Any {
        return yData[index]
    }

    override fun getY(index: Int): Float {
        return yData[index]
    }

}
