package com.tiago.blockchain.util.formatter

import com.github.mikephil.charting.formatter.ValueFormatter

class PriceAxisFormatter(private val format: String) : ValueFormatter() {

    override fun getFormattedValue(value: Float): String {
        val price = (value/1000).toInt()
        return String.format(format, price)
    }

}