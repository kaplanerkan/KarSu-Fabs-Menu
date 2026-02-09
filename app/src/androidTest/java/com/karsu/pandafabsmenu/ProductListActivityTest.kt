package com.karsu.pandafabsmenu

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.karsu.pandafabsmenu.adapter.ProductAdapter
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ProductListActivityTest {

    private lateinit var scenario: ActivityScenario<ProductListActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(ProductListActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun activity_launches_successfully() {
        onView(withId(R.id.coordinator))
            .check(matches(isDisplayed()))
    }

    @Test
    fun toolbar_isDisplayed() {
        onView(withId(R.id.toolbar))
            .check(matches(isDisplayed()))
    }

    @Test
    fun recyclerView_isDisplayed() {
        onView(withId(R.id.recyclerProducts))
            .check(matches(isDisplayed()))
    }

    @Test
    fun firstProduct_displaysWirelessHeadphones() {
        onView(withId(R.id.recyclerProducts))
            .check(matches(hasDescendant(withText("Wireless Headphones"))))
    }

    @Test
    fun secondProduct_displaysSmartWatchPro() {
        onView(withId(R.id.recyclerProducts))
            .check(matches(hasDescendant(withText("Smart Watch Pro"))))
    }

    @Test
    fun recyclerView_canScrollToLastItem() {
        onView(withId(R.id.recyclerProducts))
            .perform(
                RecyclerViewActions.scrollToPosition<ProductAdapter.ProductViewHolder>(4)
            )
    }

    @Test
    fun lastProduct_displaysWirelessCharger() {
        onView(withId(R.id.recyclerProducts))
            .perform(
                RecyclerViewActions.scrollToPosition<ProductAdapter.ProductViewHolder>(4)
            )
        onView(withId(R.id.recyclerProducts))
            .check(matches(hasDescendant(withText("Wireless Charger"))))
    }

    @Test
    fun productPrice_isDisplayed() {
        onView(withId(R.id.recyclerProducts))
            .check(matches(hasDescendant(withText("$149.99"))))
    }
}
