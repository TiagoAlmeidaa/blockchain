package com.tiago.blockchain.data.network.repository

import com.tiago.blockchain.data.network.BlockChainService
import com.tiago.blockchain.model.vo.MarketPrice
import com.tiago.blockchain.util.PeriodEnum
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class BlockChainRepositoryTest {

    private val dispatcher = TestCoroutineDispatcher()

    private lateinit var repository: BlockChainRepository

    @MockK
    lateinit var dataSource: BlockChainService

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        Dispatchers.setMain(dispatcher)

        repository = BlockChainRepository(dataSource)
    }

    @Test(expected = Exception::class)
    fun `fetchMarketPrices() should throw exception`() = runBlockingTest {
        val period = PeriodEnum.ONE_MONTH

        coEvery {
            dataSource.fetchMarketPrice(period.timeSpan)
        }.throws(Exception())

        repository.fetchMarketPrices(PeriodEnum.ONE_MONTH)
    }

    @Test
    fun `fetchMarketPrices() should return the expected MarketPrice`() = runBlockingTest {
        val expectedMarketPrice = MarketPrice()
        val expectedResponse = mockk<Response<MarketPrice>>()
        val period = PeriodEnum.ONE_MONTH

        every {
            expectedResponse.body()
        }.returns(expectedMarketPrice)

        coEvery {
            dataSource.fetchMarketPrice(period.timeSpan)
        }.returns(expectedResponse)

        val response = repository.fetchMarketPrices(PeriodEnum.ONE_MONTH)

        assertEquals(expectedMarketPrice, response)
    }

}