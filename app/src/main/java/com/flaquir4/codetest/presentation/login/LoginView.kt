package com.flaquir4.codetest.presentation.login

interface LoginView {
    fun navigateToMainScreen()
    fun showRetryOption()
    fun showBadCredentialsError()
}
