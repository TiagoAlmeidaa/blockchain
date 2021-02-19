package com.tiago.blockchain.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.tiago.blockchain.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun onLineChartButton_performClick_shouldShowFragmentLineChartContainer() {
        onView(withId(R.id.line_chart_button)).perform(click())

        onView(withId(R.id.fragment_line_chart_container)).check(matches(isDisplayed()))
    }

    @Test
    fun onBarChartButton_performClick_shouldShowFragmentBarChartContainer() {
        onView(withId(R.id.bar_chart_button)).perform(click())

        onView(withId(R.id.fragment_bar_chart_container)).check(matches(isDisplayed()))
    }

}