package com.chilangolabs.bitsopricechecker.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Gorro on 08/05/17.
 */

val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
val DATE_FORMAT_FINAL = "dd/MM/yyyy"

fun String.getDateF(): String? {
    val dateFormat: DateFormat = SimpleDateFormat(DATE_FORMAT, Locale.ENGLISH)
    val dateFormatFinal: DateFormat = SimpleDateFormat(DATE_FORMAT_FINAL, Locale.ENGLISH)
    return dateFormatFinal.format(dateFormat.parse(this))
}