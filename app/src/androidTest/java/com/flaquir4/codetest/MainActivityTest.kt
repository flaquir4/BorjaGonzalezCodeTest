package com.flaquir4.codetest

import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers
import cat.helm.result.asSuccess
import com.flaquir4.codetest.data.AuthenticationRepository
import com.flaquir4.codetest.di.DataModule
import com.flaquir4.codetest.presentation.login.LoginActivity
import com.flaquir4.codetest.presentation.main.MainActivity
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
class MainActivityTest : ScreenshotTest {

    @get:Rule
    var activityRule: IntentsTestRule<MainActivity> =
        IntentsTestRule(MainActivity::class.java, true, false)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    @JvmField
    var authenticationRepository: AuthenticationRepository = mockk(relaxed = true)


    @Test
    fun mainActivityIsShowProperly() {
        val mainActivity = activityRule.launchActivity(null)

        compareScreenshot(mainActivity);
    }

    @Test
    fun mainActivityMustNavigateToLoginScreenIfThereIsNoErrorOnLogout() {
        givenASuccessfulLogout()

        activityRule.launchActivity(null)
        Espresso.onView(ViewMatchers.withId(R.id.logoutButton)).perform(ViewActions.click())

        Intents.intended(IntentMatchers.hasComponent(LoginActivity::class.java.canonicalName))
    }

    private fun givenASuccessfulLogout() {
        coEvery { authenticationRepository.logout() }.coAnswers {
            Unit.asSuccess()
        }
        coEvery { authenticationRepository.isUserLoggedIn() }.coAnswers { false.asSuccess() }
    }

}