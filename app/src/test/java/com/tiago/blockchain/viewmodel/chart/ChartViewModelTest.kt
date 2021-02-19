package com.tiago.blockchain.viewmodel.chart

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.tiago.blockchain.data.network.repository.BlockChainRepository
import com.tiago.blockchain.model.state.BlockChainState
import com.tiago.blockchain.model.vo.MarketPrice
import com.tiago.blockchain.rule.RxSchedulerRule
import com.tiago.blockchain.util.PeriodEnum
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ChartViewModelTest {

    @get:Rule
    val executorRule = InstantTaskExecutorRule()

    @get:Rule
    var rxSchedulerRule: RxSchedulerRule = RxSchedulerRule()

    private val dispatcher = TestCoroutineDispatcher()

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

        Dispatchers.setMain(dispatcher)

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

        coEvery {
            repository.fetchMarketPrices(expectedPeriod)
        }.returns(mockk())

        viewModel.fetchMarketPrices(expectedPeriod)

        val result = viewModel.getLastPeriod()

        assertEquals(expectedPeriod, result)
    }

    @Test
    fun `fetchMarketPrices() should catch the exception correctly`() {
        val period = PeriodEnum.ONE_YEAR
        val expectedException = Exception("Error while testing")

        coEvery {
            repository.fetchMarketPrices(period)
        }.throws(expectedException)

        viewModel.fetchMarketPrices(period)

        verify(exactly = 1) { observerState.onChanged(BlockChainState.OnLoading) }
        verify(exactly = 1) { observerPeriod.onChanged(period) }
        verify(exactly = 1) { observerState.onChanged(
            BlockChainState.OnMarketPriceFetchFailed(
                expectedException
            )
        ) }
    }

    @Test
    fun `fetchMarketPrices() should receive the MarketPrice correctly`() {
        val expectedResult = MarketPrice()
        val expectedPeriod = PeriodEnum.ONE_WEEK

        coEvery {
            repository.fetchMarketPrices(expectedPeriod)
        }.returns(expectedResult)

        viewModel.fetchMarketPrices(expectedPeriod)

        verify(exactly = 1) { observerState.onChanged(BlockChainState.OnLoading) }
        verify(exactly = 1) { observerPeriod.onChanged(expectedPeriod) }
        verify(exactly = 1) { observerState.onChanged(
            BlockChainState.OnMarketPriceReceived(
                expectedResult
            )
        ) }
    }

}