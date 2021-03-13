package com.tiago.blockchain.data.network.repository

import com.tiago.blockchain.data.network.BlockChainService
import com.tiago.blockchain.model.vo.ApiError
import com.tiago.blockchain.model.vo.BlockChainResponse
import com.tiago.blockchain.util.HttpCodes
import com.tiago.blockchain.util.PeriodEnum
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.net.UnknownHostException
import javax.inject.Inject

class BlockChainRepository @Inject constructor(private val dataSource: BlockChainService) {

    fun fetchMarketPrices(period: PeriodEnum): Observable<BlockChainResponse> =
        dataSource
            .fetchMarketPrice(period.timeSpan)
            .subscribeOn(Schedulers.io())
            .map { marketPrice ->
                BlockChainResponse(marketPrice = marketPrice)
            }
            .onErrorReturn { exception ->
                val error: ApiError = when (exception) {
                    is UnknownHostException -> ApiError.ConnectionError
                    is HttpException -> when (exception.code()) {
                        HttpCodes.NOT_FOUND -> ApiError.NotFoundError
                        HttpCodes.INTERNAL -> ApiError.InternalError
                        else -> ApiError.ExceptionNotMapped
                    }
                    else -> ApiError.ExceptionNotMapped
                }
                BlockChainResponse(error = error)
            }.toObservable()

}