package com.flaquir4.codetest

import android.content.pm.ActivityInfo
import androidx.test.espresso.intent.rule.IntentsTestRule
import com.flaquir4.codetest.presentation.LoginActivity
import com.flaquir4.codetest.presentation.MainActivity
import com.karumi.shot.ScreenshotTest
import org.junit.Rule
import org.junit.Test


class LoginActivityTest: ScreenshotTest {

    @get:Rule
     var activityRule: IntentsTestRule<LoginActivity> =
        IntentsTestRule(LoginActivity::class.java, true, false)

    @Test
    fun loginActivityIsShowProperly(){
        val loginActivity = activityRule.launchActivity(null)

        compareScreenshot(loginActivity);
    }

    @Test
    fun loginActivityIsShowProperlyInLandscape(){
        val loginActivity = activityRule.launchActivity(null)

        loginActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        compareScreenshot(loginActivity);
    }
}