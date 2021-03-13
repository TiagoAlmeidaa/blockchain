package com.tiago.blockchain.viewmodel.chart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tiago.blockchain.data.network.repository.BlockChainRepository
import com.tiago.blockchain.model.state.BlockChainState
import com.tiago.blockchain.model.vo.ApiError
import com.tiago.blockchain.model.vo.BlockChainResponse
import com.tiago.blockchain.model.vo.MarketPrice
import com.tiago.blockchain.rule.RxSchedulerRule
import com.tiago.blockchain.util.PeriodEnum
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.reactivex.rxjava3.core.Observable
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ChartViewModelTest {

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @get:Rule
    var rxSchedulerRule: RxSchedulerRule = RxSchedulerRule()

    private lateinit var viewModel: ChartViewModel

    @MockK
    lateinit var repository: BlockChainRepository

    @MockK(relaxed = true)
    lateinit var observerState: Observer<BlockChainState>

    @MockK(relaxed = true)
    lateinit var observerPeriod: Observer<PeriodEnum>

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        viewModel = ChartViewModel(repository).apply {
            state.observeForever(observerState)
            lastPeriod.observeForever(observerPeriod)
        }
    }

    @After
    fun finish() = with(viewModel) {
        state.removeObserver(observerState)
        lastPeriod.removeObserver(observerPeriod)
    }

    @Test
    fun `getLastPeriod() should return ONE_MONTH when has no value in it`() {
        val result = viewModel.getLastPeriod()

        assertEquals(PeriodEnum.ONE_MONTH, result)
    }

    @Test
    fun `getLastPeriod() should return the period passed into fetchMarketPrices() parameter`() {
        val expectedPeriod = PeriodEnum.ONE_WEEK

        every {
            repository.fetchMarketPrices(expectedPeriod)
        }.returns(Observable.error(Throwable()))

        viewModel.fetchMarketPrices(expectedPeriod)

        val result = viewModel.getLastPeriod()

        assertEquals(expectedPeriod, result)
    }

    @Test
    fun `fetchMarketPrices() s`() {
        val period = PeriodEnum.ONE_YEAR
        val marketPrice = MarketPrice()
        val response = BlockChainResponse(marketPrice = marketPrice)

        every {
            repository.fetchMarketPrices(period)
        }.returns(Observable.just(response))

        viewModel.fetchMarketPrices(period)

        verify(exactly = 1) { observerState.onChanged(BlockChainState.OnLoading) }
        verify(exactly = 1) { observerPeriod.onChanged(period) }
        verify(exactly = 1) {
            observerState.onChanged(
                BlockChainState.OnMarketPriceReceived(
                    marketPrice
                )
            )
        }
    }

    @Test
    fun `fetchMarketPrices() c`() {
        val period = PeriodEnum.ONE_YEAR
        val error = ApiError.InternalError
        val response = BlockChainResponse(error = error)

        every {
            repository.fetchMarketPrices(period)
        }.returns(Observable.just(response))

        viewModel.fetchMarketPrices(period)

        verify(exactly = 1) { observerState.onChanged(BlockChainState.OnLoading) }
        verify(exactly = 1) { observerPeriod.onChanged(period) }
        verify(exactly = 1) { observerState.onChanged(BlockChainState.OnMarketPriceFetchFailed(error)) }
    }

    @Test
    fun `fetchMarketPrices() d`() {
        val period = PeriodEnum.ONE_YEAR
        val response = BlockChainResponse()

        every {
            repository.fetchMarketPrices(period)
        }.returns(Observable.just(response))

        viewModel.fetchMarketPrices(period)

        verify(exactly = 1) { observerState.onChanged(BlockChainState.OnLoading) }
        verify(exactly = 1) { observerPeriod.onChanged(period) }
        verify(exactly = 1) { observerState.onChanged(BlockChainState.InvalidResponse) }
    }

}