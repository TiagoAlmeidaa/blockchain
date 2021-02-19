package com.tiago.blockchain.data.network

import com.tiago.blockchain.model.vo.MarketPrice
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface BlockChainService {

    @GET("charts/market-price?")
    suspend fun fetchMarketPrice(
        @Query("timespan") timeSpan: String,
        @Query("format") format: String = "json"
    ): Response<MarketPrice>

}