package com.tiago.blockchain.di

import com.tiago.blockchain.di.module.NetworkModule
import com.n26.blockchain.ui.fragment.BaseChartFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class
])
interface AppComponent {
    fun inject(fragment: BaseChartFragment)
}