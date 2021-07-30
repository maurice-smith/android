package com.kingdom.utils

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingResource
import androidx.test.espresso.ViewFinder
import org.hamcrest.Matcher
import java.lang.Exception
import java.lang.reflect.Field

class VisibilityIdlingResource(private val matcher: Matcher<View>): IdlingResource {
    private var idlingCallback: IdlingResource.ResourceCallback? = null

    override fun getName(): String = "${this.javaClass::getSimpleName}"

    override fun isIdleNow(): Boolean {
        val viewFromMatcher = getViewFromMatcher(matcher)
        val isIdle = viewFromMatcher == null || viewFromMatcher.isShown

        if (isIdle) {
            idlingCallback?.onTransitionToIdle()
        }
        return isIdle
    }

    private fun getViewFromMatcher(matcher: Matcher<View>): View? {
        return try {
            val interaction = onView(matcher)
            val field: Field = interaction.javaClass.getDeclaredField("viewFinder")
            field.isAccessible = true
            val viewFinder = field.get(interaction) as ViewFinder
            viewFinder.view
        } catch (exception: Exception) {
            null
        }
    }

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        idlingCallback = callback
    }

    companion object {
        fun getVisibilityIdlingResource(matcher: Matcher<View>, resourceCallback: IdlingResource.ResourceCallback? = null): VisibilityIdlingResource {
            val idlingResource = VisibilityIdlingResource(matcher)
            idlingResource.registerIdleTransitionCallback(resourceCallback)
            return idlingResource
        }
    }
}