package com.flaquir4.codetest.presentation.login

import com.flaquir4.codetest.domain.LoginUseCase
import com.flaquir4.codetest.domain.errors.AuthenticationErrors
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginPresenter @Inject constructor(
    private val view: LoginView,
    private val loginUseCase: LoginUseCase
) : CoroutineScope by MainScope() {

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
                    is AuthenticationErrors.NetworkError -> view.showRetryOption()
                    is AuthenticationErrors.BadCredentials -> view.showBadCredentialsError()
                }
            }
        }
    }

    fun onDestroy() {
        cancel()
    }
}
