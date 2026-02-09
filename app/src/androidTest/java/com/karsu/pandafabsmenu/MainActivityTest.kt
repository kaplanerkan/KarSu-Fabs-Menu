package com.karsu.pandafabsmenu

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    private lateinit var scenario: ActivityScenario<MainActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(MainActivity::class.java)
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
    fun settingsButton_isDisplayed() {
        onView(withId(R.id.btnSettings))
            .check(matches(isDisplayed()))
    }

    @Test
    fun productsButton_isDisplayed() {
        onView(withId(R.id.btnProducts))
            .check(matches(isDisplayed()))
    }

    @Test
    fun fabsMenu_isDisplayed() {
        onView(withId(R.id.fabsMenu))
            .check(matches(isDisplayed()))
    }

    @Test
    fun fabsMenuLayout_isDisplayed() {
        onView(withId(R.id.fabsMenuLayout))
            .check(matches(isDisplayed()))
    }

    @Test
    fun settingsButton_clickOpensBottomSheet() {
        onView(withId(R.id.btnSettings))
            .perform(click())

        // BottomSheet settings should be visible
        onView(withId(R.id.seekOverlayAlpha))
            .check(matches(isDisplayed()))
    }

    @Test
    fun productsButton_clickNavigatesToProductList() {
        Intents.init()
        try {
            onView(withId(R.id.btnProducts))
                .perform(click())

            Intents.intended(hasComponent(ProductListActivity::class.java.name))
        } finally {
            Intents.release()
        }
    }
}
