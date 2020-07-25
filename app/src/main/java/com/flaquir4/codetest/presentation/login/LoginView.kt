package com.flaquir4.codetest.presentation.login

import com.flaquir4.codetest.presentation.login.validator.LoginError

interface LoginView {
    fun navigateToMainScreen()
    fun showRetryOption()
    fun showBadCredentialsError()
    fun showLoading()
    fun hideLoading()
    fun handleFormErrors(formErrors: List<LoginError>)
}
