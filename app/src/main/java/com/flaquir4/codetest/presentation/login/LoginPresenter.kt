package com.flaquir4.codetest.presentation.login

import com.flaquir4.codetest.domain.IsUserLoggedIn
import com.flaquir4.codetest.domain.LoginUseCase
import com.flaquir4.codetest.domain.errors.AuthenticationError
import com.flaquir4.codetest.presentation.base.CoroutinePresenter
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    private val view: LoginView,
    private val loginUseCase: LoginUseCase,
    private val isUserLoggedIn: IsUserLoggedIn
) : CoroutinePresenter() {

    fun onStart() {
        launch {
            val result = isUserLoggedIn()
            result.success {
                if (it) {
                    view.navigateToMainScreen()
                }
            }
        }
    }

    fun onLogInButtonTap(username: String, password: String) {
        launch {
            view.showLoading()
            val result = loginUseCase(username, password)
            view.hideLoading()
            result.success {
                view.navigateToMainScreen()
            }
            result.failure { error ->
                when (error) {
                    is AuthenticationError.NetworkError -> view.showRetryOption()
                    is AuthenticationError.BadCredentials -> view.showBadCredentialsError()
                }
            }
        }
    }

    fun onDestroy() {
        cancel()
    }
}
