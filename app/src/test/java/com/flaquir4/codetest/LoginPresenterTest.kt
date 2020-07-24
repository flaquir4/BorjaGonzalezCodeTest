package com.flaquir4.codetest

import cat.helm.result.asFailure
import cat.helm.result.asSuccess
import com.flaquir4.codetest.domain.IsUserLoggedIn
import com.flaquir4.codetest.domain.LoginUseCase
import com.flaquir4.codetest.domain.errors.AuthenticationError
import com.flaquir4.codetest.presentation.login.LoginPresenter
import com.flaquir4.codetest.presentation.login.LoginView
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

class LoginPresenterTest {

    companion object {
        const val ANY_PASSWORD = "aVerySecretPassword"
        const val ANY_USERNAME = "myself"
    }

    var view: LoginView = mockk(relaxed = true)

    var loginUseCase: LoginUseCase = mockk()
    var isUserLoggedIn: IsUserLoggedIn = mockk()

    lateinit var presenter: LoginPresenter

    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        presenter = LoginPresenter(view, loginUseCase, isUserLoggedIn)
    }

    @Test
    fun `should navigate to login if login process is successful`() {
        givenLoginIsSuccessful()

        presenter.onLogInButtonTap(ANY_USERNAME, ANY_PASSWORD)

        coVerify(exactly = 1) { view.navigateToMainScreen() }
    }

    @Test
    fun `should show retry option if login process returns network error`() {
        givenLoginReturnsNetworkError()

        presenter.onLogInButtonTap(ANY_USERNAME, ANY_PASSWORD)

        coVerify(exactly = 1) { view.showRetryOption() }
    }

    @Test
    fun `should show invalid credentials if login process returns invalid credentials`() {
        givenLoginReturnsBadCredentialsError()

        presenter.onLogInButtonTap(ANY_USERNAME, ANY_PASSWORD)

        coVerify(exactly = 1) { view.showBadCredentialsError() }
    }

    @Test
    fun `should navigate to main screen if user is logged in`() {
        givenUserIsLoggedIn()

        presenter.onStart()

        coVerify(exactly = 1) { view.navigateToMainScreen() }
    }

    @Test
    fun `should not navigate to main screen if user is not logged in`() {
        givenUserIsNotLoggedIn()

        presenter.onStart()

        coVerify(exactly = 0) { view.navigateToMainScreen() }

    }

    private fun givenLoginIsSuccessful() {
        coEvery { loginUseCase(any(), any()) }.coAnswers { Unit.asSuccess() }
    }

    private fun givenLoginReturnsNetworkError() {
        coEvery {
            loginUseCase(
                any(),
                any()
            )
        }.coAnswers { AuthenticationError.NetworkError.asFailure() }
    }

    private fun givenLoginReturnsBadCredentialsError() {
        coEvery {
            loginUseCase(
                any(),
                any()
            )
        }.coAnswers { AuthenticationError.BadCredentials.asFailure() }
    }

    private fun givenUserIsLoggedIn() = setUserLoggedIn(true)

    private fun givenUserIsNotLoggedIn() = setUserLoggedIn(false)

    private fun setUserLoggedIn(boolean: Boolean) {
        coEvery { isUserLoggedIn() }.coAnswers { boolean.asSuccess() }
    }

}