package com.tiago.blockchain.data.network

import com.tiago.blockchain.model.vo.MarketPrice
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface BlockChainService {

    @GET("charts/market-price?")
    fun fetchMarketPrice(
        @Query("timespan") timeSpan: String,
        @Query("format") format: String = "json"
    ): Single<MarketPrice>

}