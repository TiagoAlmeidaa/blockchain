package com.tiago.blockchain.viewmodel.chart

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tiago.blockchain.data.network.repository.BlockChainRepository
import com.tiago.blockchain.model.state.BlockChainState
import com.tiago.blockchain.util.PeriodEnum
import com.tiago.blockchain.util.SingleLiveEvent
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import javax.inject.Inject

class ChartViewModel @Inject constructor(
    private val repository: BlockChainRepository
) : ViewModel() {

    private val _state = SingleLiveEvent<BlockChainState>()
    val state = _state as LiveData<BlockChainState>

    private val _lastPeriod = SingleLiveEvent<PeriodEnum>()
    val lastPeriod = _lastPeriod as LiveData<PeriodEnum>

    private val disposables: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    fun fetchMarketPrices(period: PeriodEnum) {
        disposables.add(
            repository
                .fetchMarketPrices(period)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { response ->
                    with(response) {
                        val state = when {
                            marketPrice != null -> BlockChainState.OnMarketPriceReceived(marketPrice)
                            error != null -> BlockChainState.OnMarketPriceFetchFailed(error)
                            else -> BlockChainState.InvalidResponse
                        }
                        _state.postValue(state)
                    }
                }
        )
    }

    fun getLastPeriod(): PeriodEnum = _lastPeriod.value ?: PeriodEnum.ONE_MONTH

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}