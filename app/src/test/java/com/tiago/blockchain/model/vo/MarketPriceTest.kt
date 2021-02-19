package com.tiago.blockchain.model.vo

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MarketPriceTest {

    private lateinit var marketPrice: MarketPrice

    @Test
    fun `should create market price object correctly`() {
        val priceOne = Price(1L, 1.0)
        val priceTwo = Price(2L, 2.0)

        val expectedName = "Testing name"
        val expectedUnit = "Testing unit"
        val expectedPrices = mutableListOf(priceOne, priceTwo)

        marketPrice = MarketPrice(expectedName, expectedUnit, expectedPrices)

        assertEquals(expectedName, marketPrice.name)
        assertEquals(expectedUnit, marketPrice.unit)
        assertEquals(expectedPrices, marketPrice.prices)
    }

    @Test
    fun `convertPricesToEntry() should generate the correct Entry list from the Prices`() {
        val priceOne = Price(1L, 1.0)
        val priceTwo = Price(2L, 2.0)

        val expectedPrices = mutableListOf(priceOne, priceTwo)

        marketPrice = MarketPrice(prices = expectedPrices)

        val result = marketPrice.convertPricesToEntry()

        assertEquals(expectedPrices.size, result.size)

        assertEquals(expectedPrices[0].dateInMillis.toFloat(), result[0].x)
        assertEquals(expectedPrices[0].price.toFloat(), result[0].y)

        assertEquals(expectedPrices[1].dateInMillis.toFloat(), result[1].x)
        assertEquals(expectedPrices[1].price.toFloat(), result[1].y)
    }

    @Test
    fun `convertPricesToBarEntry() should generate the correct BarEntry list from the Prices`() {
        val priceOne = Price(5L, 5.55)
        val priceTwo = Price(10L, 2.22)

        val expectedPrices = mutableListOf(priceOne, priceTwo)

        marketPrice = MarketPrice(prices = expectedPrices)

        val result = marketPrice.convertPricesToBarEntry()

        assertEquals(expectedPrices.size, result.size)

        assertEquals(expectedPrices[0].dateInMillis.toFloat(), result[0].x)
        assertEquals(expectedPrices[0].price.toFloat(), result[0].y)

        assertEquals(expectedPrices[1].dateInMillis.toFloat(), result[1].x)
        assertEquals(expectedPrices[1].price.toFloat(), result[1].y)
    }

}