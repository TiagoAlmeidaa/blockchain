package com.tiago.blockchain.model.vo

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class PriceTest {

    private lateinit var price: Price

    @Test
    fun `should create price object correctly`() {
        val expectedDateInMillis = 15432103L
        val expectedPrice = 15.0

        price = Price(expectedDateInMillis, expectedPrice)

        assertEquals(expectedDateInMillis, price.dateInMillis)
        assertEquals(expectedPrice, price.price, 0.0)
    }

}