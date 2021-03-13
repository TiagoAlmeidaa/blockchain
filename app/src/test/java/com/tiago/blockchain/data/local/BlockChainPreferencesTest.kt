package com.tiago.blockchain.data.local

import android.content.Context
import android.content.SharedPreferences
import com.tiago.blockchain.data.network.repository.BlockChainRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.just
import io.mockk.runs
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class BlockChainPreferencesTest {

    companion object {
        private const val KEY_PREFERENCES = "key.preferences"
        private const val KEY_ON_BOARDING = "key.on.boarding"
    }

    @MockK
    lateinit var context: Context

    @MockK
    lateinit var sharedPreferences: SharedPreferences

    @MockK
    lateinit var editor: SharedPreferences.Editor

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `getShowOnBoarding() should return false`() {
        every {
            sharedPreferences.getBoolean(KEY_ON_BOARDING, true)
        }.returns(false)

        every {
            context.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE)
        }.returns(sharedPreferences)

        val result = BlockChainPreferences.getShowOnBoarding(context)

        assertFalse(result)
    }

    @Test
    fun `getShowOnBoarding() should return true`() {
        every {
            sharedPreferences.getBoolean(KEY_ON_BOARDING, true)
        }.returns(true)

        every {
            context.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE)
        }.returns(sharedPreferences)

        val result = BlockChainPreferences.getShowOnBoarding(context)

        assertTrue(result)
    }

    @Test
    fun `setShowOnBoarding() should just run`() {
        every {
            editor.apply()
        }.just(runs)

        every {
            editor.putBoolean(KEY_ON_BOARDING, true)
        }.returns(editor)

        every {
            sharedPreferences.edit()
        }.returns(editor)

        every {
            context.getSharedPreferences(KEY_PREFERENCES, Context.MODE_PRIVATE)
        }.returns(sharedPreferences)

        BlockChainPreferences.setShowOnBoarding(context, true)
    }

}