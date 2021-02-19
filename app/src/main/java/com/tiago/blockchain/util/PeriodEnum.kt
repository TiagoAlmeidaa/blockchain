package com.tiago.blockchain.util

enum class PeriodEnum(
    val timeSpan: String,
    val days: String
) {
    ONE_WEEK("1week", "7 days"),
    ONE_MONTH("30days", "30 days"),
    ONE_YEAR("1year", "365 days")
}