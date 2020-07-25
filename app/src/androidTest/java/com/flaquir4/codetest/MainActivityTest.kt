package com.flaquir4.codetest

import androidx.test.espresso.intent.rule.IntentsTestRule
import com.flaquir4.codetest.presentation.main.MainActivity
import com.karumi.shot.ScreenshotTest
import org.junit.Rule
import org.junit.Test


class MainActivityTest: ScreenshotTest {

    @get:Rule
     var activityRule: IntentsTestRule<MainActivity> =
        IntentsTestRule(MainActivity::class.java, true, false)

    @Test
    fun mainActivityIsShowProperly(){
        val mainActivity = activityRule.launchActivity(null)

        compareScreenshot(mainActivity);
    }
}