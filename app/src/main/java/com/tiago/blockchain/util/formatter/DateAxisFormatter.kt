package com.tiago.blockchain.util.formatter

import android.text.format.DateFormat
import com.github.mikephil.charting.formatter.ValueFormatter

class DateAxisFormatter : ValueFormatter() {

    companion object {
        private const val DATE_FORMAT = "dd MMM y"
    }

    override fun getFormattedValue(value: Float): String {
        return DateFormat.format(DATE_FORMAT, value.toLong() * 1000).toString()
    }

}