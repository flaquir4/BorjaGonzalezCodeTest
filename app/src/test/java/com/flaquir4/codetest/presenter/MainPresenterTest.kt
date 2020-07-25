package com.flaquir4.codetest.presenter

import cat.helm.result.asSuccess
import com.flaquir4.codetest.domain.LogoutUseCase
import com.flaquir4.codetest.presentation.main.MainPresenter
import com.flaquir4.codetest.presentation.main.MainView
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class MainPresenterTest {

    private val view: MainView = mockk()
    private val logoutUseCase: LogoutUseCase = mockk()

    lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        presenter = MainPresenter(view, logoutUseCase)
    }

    @Test
    fun `should navigate to login if logout is successful`() {
        givenLogoutIsSuccessful()

        presenter.onLogoutButtonTap()

        coVerify(exactly = 1) { view.navigateToLoginScreen() }
    }

    private fun givenLogoutIsSuccessful() {
        coEvery { logoutUseCase() }.coAnswers { Unit.asSuccess() }
    }
}
