package com.tiago.blockchain.rule

import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.internal.schedulers.TrampolineScheduler
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RxSchedulerRule : TestRule {

    override fun apply(base: Statement?, description: Description?): Statement {
        return object : Statement() {
            override fun evaluate() {
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { TrampolineScheduler.instance() }
                RxJavaPlugins.setIoSchedulerHandler { TrampolineScheduler.instance() }
                RxJavaPlugins.setComputationSchedulerHandler { TrampolineScheduler.instance() }
                RxJavaPlugins.setErrorHandler {}
                try {
                    base!!.evaluate()
                } finally {
                    RxAndroidPlugins.reset()
                    RxJavaPlugins.reset()
                }
            }
        }
    }

}