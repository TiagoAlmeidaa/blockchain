package com.tiago.blockchain.data.network.repository

import com.tiago.blockchain.data.network.BlockChainService
import com.tiago.blockchain.model.vo.MarketPrice
import com.tiago.blockchain.util.PeriodEnum
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class BlockChainRepository @Inject constructor(
    private val dataSource: BlockChainService
) {

    fun fetchMarketPrices(period: PeriodEnum): Observable<MarketPrice> =
        dataSource
            .fetchMarketPrice(period.timeSpan)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable()

}