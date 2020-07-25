package com.flaquir4.codetest

import android.content.pm.ActivityInfo
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.closeSoftKeyboard
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import cat.helm.result.asFailure
import cat.helm.result.asSuccess
import com.flaquir4.codetest.data.AuthenticationRepository
import com.flaquir4.codetest.di.DataModule
import com.flaquir4.codetest.domain.errors.AuthenticationError
import com.flaquir4.codetest.presentation.login.LoginActivity
import com.flaquir4.codetest.presentation.main.MainActivity
import com.karumi.shot.ScreenshotTest
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@HiltAndroidTest
@UninstallModules(DataModule::class)
class LoginActivityTest : ScreenshotTest {

    companion object {
        const val AN_EMAIL = "flaquir4@gmail.com"
        const val A_PASSWORD= "12345678"
    }

    @get:Rule
    var activityRule: ActivityTestRule<LoginActivity> =
        ActivityTestRule(LoginActivity::class.java, true, false)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    @JvmField
    var authenticationRepository: AuthenticationRepository = mockk(relaxed = true)

    @Test
    fun loginActivityIsShowProperly() {
        givenThereIsNoToken()

        val loginActivity = activityRule.launchActivity(null)

        compareScreenshot(loginActivity);
    }

    @Before
    fun setUp() {
        Intents.init();
    }

    @After
    fun tearDown() {
        Intents.release()
    }

    @Test
    fun loginActivityIsShowProperlyInLandscape() {
        givenThereIsNoToken()

        val loginActivity = activityRule.launchActivity(null)
        loginActivity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        compareScreenshot(loginActivity);
    }

    @Test
    fun loginActivityMustShowProperErrorIfItReturnsNetworkError() {
        givenThereIsNoToken()
        givenLoginReturnsNetworkError()

        val loginActivity = activityRule.launchActivity(null)
        onView(ViewMatchers.withId(R.id.usernameEditText)).perform(typeText(AN_EMAIL))
        onView(ViewMatchers.withId(R.id.passwordEditText)).perform(
            typeText(A_PASSWORD),
            closeSoftKeyboard()
        )
        onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click())

        compareScreenshot(loginActivity);
    }

    @Test
    fun loginActivityMustShowProperErrorIfItReturnsBadCredentialsError() {
        givenThereIsNoToken()
        givenLoginReturnsBadCredentialsError()

        val loginActivity = activityRule.launchActivity(null)
        onView(ViewMatchers.withId(R.id.usernameEditText)).perform(typeText(AN_EMAIL))
        onView(ViewMatchers.withId(R.id.passwordEditText)).perform(
            typeText(A_PASSWORD),
            closeSoftKeyboard()
        )
        onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click())

        compareScreenshot(loginActivity);
    }

    @Test
    fun loginActivityMustNavigateToMainScreenIfThereIsNoError() {
        givenThereIsNoToken()
        givenASuccessfulLogin()

        activityRule.launchActivity(null)
        onView(ViewMatchers.withId(R.id.usernameEditText)).perform(typeText(AN_EMAIL))
        onView(ViewMatchers.withId(R.id.passwordEditText)).perform(
            typeText(A_PASSWORD),
            closeSoftKeyboard()
        )
        onView(ViewMatchers.withId(R.id.loginButton)).perform(ViewActions.click())

        intended(hasComponent(MainActivity::class.java.canonicalName))
    }

    @Test
    fun loginActivityMustNavigateToMainScreenIfThereIsAToken() {
        givenThereIsToken()

        activityRule.launchActivity(null)

        intended(hasComponent(MainActivity::class.java.canonicalName))
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

    private fun givenThereIsToken() = setTokenExistence(true)

    private fun givenThereIsNoToken() = setTokenExistence(false)

    private fun setTokenExistence(boolean: Boolean) {
        coEvery {
            authenticationRepository.isUserLoggedIn()
        }.coAnswers { boolean.asSuccess() }
    }

    private fun givenASuccessfulLogin() {
        coEvery {
            authenticationRepository.login(
                any(),
                any()
            )
        }.coAnswers { Unit.asSuccess() }
    }


}
