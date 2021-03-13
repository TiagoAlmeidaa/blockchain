package com.tiago.blockchain.model.state

import com.tiago.blockchain.model.vo.ApiError
import com.tiago.blockchain.model.vo.MarketPrice
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BlockChainStateTest {

    private lateinit var state: BlockChainState

    @Test
    fun `should create OnMarketPriceFetchFailed correctly`() {
        val expectedException = ApiError.ExceptionNotMapped
        val expectedState = BlockChainState.OnMarketPriceFetchFailed(expectedException)

        state = expectedState

        assertEquals(expectedState, state)
        assertEquals(expectedState.exception, expectedException)
    }

    @Test
    fun `should create OnMarketPriceReceived correctly`() {
        val expectedMarketPrice = MarketPrice()
        val expectedState = BlockChainState.OnMarketPriceReceived(expectedMarketPrice)

        state = expectedState

        assertEquals(expectedState, state)
        assertEquals(expectedState.marketPrice, expectedMarketPrice)
    }

}