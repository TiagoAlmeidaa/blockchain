package com.tiago.blockchain.data.network.repository

import com.tiago.blockchain.data.network.BlockChainService
import com.tiago.blockchain.model.vo.MarketPrice
import com.tiago.blockchain.util.PeriodEnum
import javax.inject.Inject

class BlockChainRepository @Inject constructor(
    private val dataSource: BlockChainService
) {

    suspend fun fetchMarketPrices(period: PeriodEnum): MarketPrice {
        return dataSource.fetchMarketPrice(period.timeSpan).body()!!
    }

}