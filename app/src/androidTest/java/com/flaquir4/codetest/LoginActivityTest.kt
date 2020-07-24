package com.flaquir4.codetest

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import cat.helm.result.asFailure
import com.flaquir4.codetest.data.AuthenticationRepository
import com.flaquir4.codetest.di.DataModule
import com.flaquir4.codetest.domain.errors.AuthenticationError
import com.flaquir4.codetest.presentation.login.LoginActivity
import com.karumi.shot.ScreenshotTest
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(DataModule::class)
class LoginActivityTest : ScreenshotTest {

    @get:Rule
    var activityRule: IntentsTestRule<LoginActivity> =
        IntentsTestRule(LoginActivity::class.java, true, false)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    @JvmField
    var authenticationRepository: AuthenticationRepository = mockk()

    @Test
    fun loginActivityIsShowProperly() {
        val loginActivity = activityRule.launchActivity(null)

        compareScreenshot(loginActivity);
    }

    @Test
    fun loginActivityIsShowProperlyInLandscape() {
        val loginActivity = activityRule.launchActivity(null)

        loginActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        compareScreenshot(loginActivity);
    }

    @Test
    fun loginMustShowProperErrorIfItReturnsNetworkError() {
        val loginActivity = activityRule.launchActivity(null)
        givenLoginReturnsNetworkError()

        onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click())

        compareScreenshot(loginActivity);
    }

     @Test
    fun loginMustShowProperErrorIfItReturnsBadCredentialsError() {
        givenLoginReturnsBadCredentialsError()
        val loginActivity = activityRule.launchActivity(null)

        onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click())

        compareScreenshot(loginActivity);
    }


    private fun givenLoginReturnsNetworkError() {
        coEvery {
            authenticationRepository.login(
                any(),
                any()
            )
        }.coAnswers { AuthenticationError.NetworkError.asFailure() }
    }

    private fun givenLoginReturnsBadCredentialsError() {
        coEvery {
            authenticationRepository.login(
                any(),
                any()
            )
        }.coAnswers { AuthenticationError.BadCredentials.asFailure() }
    }
}