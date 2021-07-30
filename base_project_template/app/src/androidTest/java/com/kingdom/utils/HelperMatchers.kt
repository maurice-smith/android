package com.kingdom.utils

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.base.IdlingResourceRegistry
import com.kingdom.utils.VisibilityIdlingResource.Companion.getVisibilityIdlingResource
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

fun withBackgroundDrawable(backgroundResId: Int): TypeSafeMatcher<View> = object : TypeSafeMatcher<View>()  {
    override fun describeTo(description: Description?) {
        TODO("Not yet implemented")
    }

    override fun matchesSafely(item: View?): Boolean =
        item != null
                && item.background?.constantState == item.resources.getDrawable(backgroundResId, null).constantState
}

fun waitUntilViewIsDisplayed(matcher: TypeSafeMatcher<View>) {
    val visibilityIdlingResource = getVisibilityIdlingResource(matcher)
    try {
        IdlingRegistry.getInstance().register(visibilityIdlingResource)
    } finally {
        IdlingRegistry.getInstance().unregister(visibilityIdlingResource)
    }
}

fun waitUntilViewIsDisplayedAndClick(matcher: TypeSafeMatcher<View>) {
    val visibilityIdlingResource = getVisibilityIdlingResource(matcher)
    try {
        visibilityIdlingResource.registerIdleTransitionCallback {
            onView(matcher).perform(click())
        }
        IdlingRegistry.getInstance().register(visibilityIdlingResource)
    } finally {
        IdlingRegistry.getInstance().unregister(visibilityIdlingResource)
    }
}