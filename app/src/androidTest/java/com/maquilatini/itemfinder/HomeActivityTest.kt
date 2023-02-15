package com.maquilatini.itemfinder

import android.app.SearchManager.QUERY
import android.widget.EditText
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.pressImeActionButton
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey
import androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.maquilatini.itemfinder.utils.CATEGORY_ID_KEY
import com.maquilatini.itemfinder.utils.REAL_ESTATE_CATEGORY_ID
import com.maquilatini.itemfinder.utils.SERVICES_CATEGORY_ID
import com.maquilatini.itemfinder.utils.VEHICLES_CATEGORY_ID
import com.maquilatini.itemfinder.view.home.HomeActivity
import com.maquilatini.itemfinder.view.listing.ListingActivity
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class HomeActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Before
    fun setUp() {
        Intents.init()
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun test_home_loaded() {
        onView(withText("Welcome!")).check(matches(isDisplayed()))
    }

    @Test
    fun test_featured1_categoryClick() {
        onView(withId(R.id.featured1_category_card)).perform(click())
        intended(
            allOf(
                hasComponent(ListingActivity::class.java.name),
                hasExtraWithKey(CATEGORY_ID_KEY),
                hasExtra(CATEGORY_ID_KEY, REAL_ESTATE_CATEGORY_ID)
            )
        )

        pressBack()
    }

    @Test
    fun test_featured2_categoryClick() {
        onView(withId(R.id.featured2_category_card)).perform(click())
        intended(
            allOf(
                hasComponent(ListingActivity::class.java.name),
                hasExtraWithKey(CATEGORY_ID_KEY),
                hasExtra(CATEGORY_ID_KEY, VEHICLES_CATEGORY_ID)
            )
        )

        pressBack()
    }

    @Test
    fun test_featured3Category_click() {
        onView(withId(R.id.featured3_category_card)).perform(click())
        intended(
            allOf(
                hasComponent(ListingActivity::class.java.name),
                hasExtraWithKey(CATEGORY_ID_KEY),
                hasExtra(CATEGORY_ID_KEY, SERVICES_CATEGORY_ID)
            )
        )

        pressBack()
    }

    @Test
    fun test_searchView_query() {
        val testQuery = "Macbook"
        onView(withId(R.id.searchView)).perform(click())
        onView(isAssignableFrom(EditText::class.java)).perform(typeText(testQuery))
        onView(isAssignableFrom(EditText::class.java)).perform(pressImeActionButton())

        intended(
            allOf(
                hasComponent(ListingActivity::class.java.name),
                hasExtraWithKey(QUERY),
                hasExtra(QUERY, testQuery)
            )
        )
    }

    @Test
    fun test_lastItem_notDisplayed() {
        // Last item should not exist if the list wasn't scrolled down.
        onView(withText("Otras categorías")).check(doesNotExist())
    }

    @Test
    fun test_hint_searchView() {
        onView(withId(R.id.searchView)).perform(click())
        onView(withHint(R.string.search_hint)).check(matches(isDisplayed()))
    }
}