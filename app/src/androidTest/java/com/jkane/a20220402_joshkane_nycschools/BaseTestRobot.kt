package com.jkane.a20220402_joshkane_nycschools

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry

open class BaseTestRobot {

    fun clickViewWithText(text: String): ViewInteraction =
        onView(withText(text)).perform(ViewActions.click())

    fun isHintDisplayed(hint: String): ViewInteraction = onView(withHint(hint)).check(
        ViewAssertions.matches(
            isDisplayed()
        )
    )

    fun isTextDisplayed(text: String): ViewInteraction =
        onView(withText(text)).check(ViewAssertions.matches(isDisplayed()))

    fun isStringResourceDisplayed(stringResId: Int): ViewInteraction {
        val string = InstrumentationRegistry.getInstrumentation().targetContext.resources.getString(
            stringResId
        )
        return onView(withText(string)).check(ViewAssertions.matches(isDisplayed()))
    }

    fun isViewDisplayed(viewId: Int): ViewInteraction = onView(withId(viewId))
        .check(
            ViewAssertions.matches(
                isDisplayed()
            )
        )

    fun scrollToText(text: String): ViewInteraction =
        onView(withText(text)).perform(ViewActions.scrollTo()).check(
            ViewAssertions.matches(
                isDisplayed()
            )
        )
}