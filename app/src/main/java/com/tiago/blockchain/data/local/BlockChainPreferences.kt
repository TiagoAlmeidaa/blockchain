package com.tiago.blockchain.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE

object BlockChainPreferences {

    private const val KEY_PREFERENCES = "key.preferences"
    private const val KEY_ON_BOARDING = "key.on.boarding"

    fun getShowOnBoarding(context: Context): Boolean = context
        .getSharedPreferences(KEY_PREFERENCES, MODE_PRIVATE)
        .getBoolean(KEY_ON_BOARDING, true)

    fun setShowOnBoarding(context: Context, show: Boolean) = context
        .getSharedPreferences(KEY_PREFERENCES, MODE_PRIVATE)
        .edit()
        .putBoolean(KEY_ON_BOARDING, show)
        .apply()

}