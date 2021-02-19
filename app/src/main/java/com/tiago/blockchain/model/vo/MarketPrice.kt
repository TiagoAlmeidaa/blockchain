package com.tiago.blockchain.model.vo

import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.google.gson.annotations.SerializedName

data class MarketPrice(
    val name: String = "",
    val unit: String = "",
    @SerializedName("values") val prices: List<Price> = emptyList()
) {

    fun convertPricesToEntry(): List<Entry> =
        prices.map { item -> Entry(item.dateInMillis.toFloat(), item.price.toFloat()) }

    fun convertPricesToBarEntry(): List<BarEntry> =
        prices.map { item -> BarEntry(item.dateInMillis.toFloat(), item.price.toFloat()) }

}
