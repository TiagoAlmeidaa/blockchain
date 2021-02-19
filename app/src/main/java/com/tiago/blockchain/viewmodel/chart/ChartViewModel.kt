package com.tiago.blockchain.viewmodel.chart

import androidx.lifecycle.LiveData
import com.tiago.blockchain.data.network.repository.BlockChainRepository
import com.tiago.blockchain.model.state.BlockChainState
import com.tiago.blockchain.util.PeriodEnum
import com.tiago.blockchain.util.SingleLiveEvent
import com.tiago.blockchain.viewmodel.BaseViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class ChartViewModel @Inject constructor(
    private val repository: BlockChainRepository
) : BaseViewModel() {

    private val _state = SingleLiveEvent<BlockChainState>()
    val state = _state as LiveData<BlockChainState>

    private val _lastPeriod = SingleLiveEvent<PeriodEnum>()
    val lastPeriod = _lastPeriod as LiveData<PeriodEnum>

    private val disposables: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    fun fetchMarketPrices(period: PeriodEnum) = runWithCoroutines(
        doInBackground = {
            _state.postValue(BlockChainState.OnLoading)
            _lastPeriod.postValue(period)

            disposables.add(
                Observable
                    .just(repository.fetchMarketPrices(period))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { marketPrice -> _state.postValue(BlockChainState.OnMarketPriceReceived(marketPrice)) }
            )
        },
        doOnError = { exception ->
            _state.postValue(BlockChainState.OnMarketPriceFetchFailed(exception))
        }
    )

    fun getLastPeriod(): PeriodEnum = _lastPeriod.value ?: PeriodEnum.ONE_MONTH

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}