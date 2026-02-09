package com.karsu.pandafabsmenu

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LauncherActivityTest {

    private lateinit var scenario: ActivityScenario<LauncherActivity>

    @Before
    fun setUp() {
        scenario = ActivityScenario.launch(LauncherActivity::class.java)
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Test
    fun activity_launches_successfully() {
        // Verify the activity launches without crashing
        onView(withText("KarSu FABs Menu"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun composeButton_isDisplayed() {
        onView(withText("Jetpack Compose"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun viewBasedButton_isDisplayed() {
        onView(withText("View-Based (XML)"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun subtitle_isDisplayed() {
        onView(withText("Hangi versiyonu test etmek istiyorsunuz?"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun recommendedText_isDisplayed() {
        onView(withText("Önerilen - Modern UI toolkit"))
            .check(matches(isDisplayed()))
    }

    @Test
    fun classicText_isDisplayed() {
        onView(withText("Klasik Android Views"))
            .check(matches(isDisplayed()))
    }
}
