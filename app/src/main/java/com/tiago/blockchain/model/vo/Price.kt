package com.tiago.blockchain.model.vo

import com.google.gson.annotations.SerializedName

data class Price(
    @SerializedName("x") val dateInMillis: Long,
    @SerializedName("y") val price: Double
)
