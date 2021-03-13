package com.tiago.blockchain.data.network.repository

import com.tiago.blockchain.data.network.BlockChainService
import com.tiago.blockchain.model.vo.ApiError
import com.tiago.blockchain.model.vo.BlockChainResponse
import com.tiago.blockchain.model.vo.MarketPrice
import com.tiago.blockchain.rule.RxSchedulerRule
import com.tiago.blockchain.util.PeriodEnum
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.HttpException
import java.net.UnknownHostException

@RunWith(JUnit4::class)
class BlockChainRepositoryTest {

    @get:Rule
    var rxSchedulerRule: RxSchedulerRule = RxSchedulerRule()

    private lateinit var repository: BlockChainRepository

    @MockK
    lateinit var dataSource: BlockChainService

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        repository = BlockChainRepository(dataSource)
    }

    @Test
    fun `fetchMarketPrices() should return ExceptionNotMapped for exceptions that are not expected`() {
        val exception = Exception()
        val response = BlockChainResponse(error = ApiError.ExceptionNotMapped)
        val period = PeriodEnum.ONE_MONTH

        every {
            dataSource.fetchMarketPrice(period.timeSpan)
        }.returns(Single.error(exception))

        repository
            .fetchMarketPrices(PeriodEnum.ONE_MONTH)
            .test()
            .assertValue(response)
            .dispose()
    }

    @Test
    fun `fetchMarketPrices() should return ConnectionError for exceptions that are UnknownHostException`() {
        val exception = UnknownHostException()
        val response = BlockChainResponse(error = ApiError.ConnectionError)
        val period = PeriodEnum.ONE_MONTH

        every {
            dataSource.fetchMarketPrice(period.timeSpan)
        }.returns(Single.error(exception))

        repository
            .fetchMarketPrices(PeriodEnum.ONE_MONTH)
            .test()
            .assertValue(response)
            .dispose()
    }

    @Test
    fun `fetchMarketPrices() should return NotFoundError for exceptions that are HttpException with code 404`() {
        val exception = mockk<HttpException>()
        val exceptionCode = 404

        every {
            exception.code()
        }.returns(exceptionCode)

        val response = BlockChainResponse(error = ApiError.NotFoundError)
        val period = PeriodEnum.ONE_MONTH

        every {
            dataSource.fetchMarketPrice(period.timeSpan)
        }.returns(Single.error(exception))

        repository
            .fetchMarketPrices(PeriodEnum.ONE_MONTH)
            .test()
            .assertValue(response)
            .dispose()
    }

    @Test
    fun `fetchMarketPrices() should return InternalError for exceptions that are HttpException with code 500`() {
        val exception = mockk<HttpException>()
        val exceptionCode = 500

        every {
            exception.code()
        }.returns(exceptionCode)

        val response = BlockChainResponse(error = ApiError.InternalError)
        val period = PeriodEnum.ONE_MONTH

        every {
            dataSource.fetchMarketPrice(period.timeSpan)
        }.returns(Single.error(exception))

        repository
            .fetchMarketPrices(PeriodEnum.ONE_MONTH)
            .test()
            .assertValue(response)
            .dispose()
    }

    @Test
    fun `fetchMarketPrices() should return ExceptionNotMapped for exceptions that are HttpException with not known code`() {
        val exception = mockk<HttpException>()
        val exceptionCode = 123

        every {
            exception.code()
        }.returns(exceptionCode)

        val response = BlockChainResponse(error = ApiError.ExceptionNotMapped)
        val period = PeriodEnum.ONE_MONTH

        every {
            dataSource.fetchMarketPrice(period.timeSpan)
        }.returns(Single.error(exception))

        repository
            .fetchMarketPrices(PeriodEnum.ONE_MONTH)
            .test()
            .assertValue(response)
            .dispose()
    }

    @Test
    fun `fetchMarketPrices() should return the expected MarketPrice`() {
        val expectedMarketPrice = MarketPrice()
        val response = BlockChainResponse(marketPrice = expectedMarketPrice)
        val period = PeriodEnum.ONE_MONTH

        every {
            dataSource.fetchMarketPrice(period.timeSpan)
        }.returns(Single.just(expectedMarketPrice))

        repository
            .fetchMarketPrices(PeriodEnum.ONE_MONTH)
            .test()
            .assertValue(response)
            .dispose()
    }

}