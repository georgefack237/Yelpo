package com.health13.yelpo

import androidx.appcompat.widget.SearchView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.health13.yelpo.presentation.activities.MainActivity
import com.health13.yelpo.presentation.activities.SearchActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SearchTest {



    @get:Rule
    val searchActivity = IntentsTestRule(MainActivity::class.java)

    @get:Rule
    val mainActivity = ActivityScenarioRule(SearchActivity::class.java)



    @Test
    fun testSearch(){
        //ACT
        Espresso.onView(withId(R.id.svSearchQuery)).perform().perform()

    }
}