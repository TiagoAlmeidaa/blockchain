package com.tiago.blockchain.matcher

import android.view.View
import com.tiago.blockchain.matcher.impl.DrawableMatcher
import org.hamcrest.Matcher

object EspressoTestsMatchers {
    fun withDrawable(resourceId: Int): Matcher<View> = DrawableMatcher(resourceId)
}