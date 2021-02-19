package com.tiago.blockchain

import android.app.Application
import com.tiago.blockchain.di.AppComponent
import com.tiago.blockchain.di.DaggerAppComponent

class BlockChainApplication : Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}