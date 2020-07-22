package com.flaquir4.codetest

import androidx.test.espresso.intent.rule.IntentsTestRule
import com.flaquir4.codetest.presentation.LoginActivity
import com.flaquir4.codetest.presentation.MainActivity
import com.karumi.shot.ScreenshotTest
import org.junit.Rule
import org.junit.Test


class LoginActivityTest: ScreenshotTest {

    @get:Rule
     var activityRule: IntentsTestRule<MainActivity> =
        IntentsTestRule(MainActivity::class.java, true, false)

    @Test
    fun loginActivityIsShowProperly(){
        val loginActivity = activityRule.launchActivity(null)

        compareScreenshot(loginActivity);
    }
}