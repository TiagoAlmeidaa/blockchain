package com.tiago.blockchain.ui.fragment.home

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.tiago.blockchain.R
import com.tiago.blockchain.matcher.EspressoTestsMatchers.withDrawable
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeFragmentTest {

    @Test
    fun launchFragment() {
        launchFragmentInContainer<HomeFragment>()

        onView(withId(R.id.block_chain_image_view))
            .check(matches(withDrawable(R.drawable.icon_block_chain)))
            .check(matches(isDisplayed()))

        onView(withId(R.id.title_text_view))
            .check(matches(withText(R.string.app_name)))
            .check(matches(isDisplayed()))

        onView(withId(R.id.line_chart_button))
            .check(matches(withText(R.string.home_fragment_line_chart_button)))
            .check(matches(isDisplayed()))

        onView(withId(R.id.bar_chart_button))
            .check(matches(withText(R.string.home_fragment_bar_chart_button)))
            .check(matches(isDisplayed()))
    }

}