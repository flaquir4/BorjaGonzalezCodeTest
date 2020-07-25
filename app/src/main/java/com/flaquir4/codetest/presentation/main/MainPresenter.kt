package com.flaquir4.codetest.presentation.main

import com.flaquir4.codetest.domain.LogoutUseCase
import com.flaquir4.codetest.presentation.base.CoroutinePresenter
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainPresenter @Inject constructor(
    private val view: MainView,
    private val logoutUseCase: LogoutUseCase
) : CoroutinePresenter() {

   fun onLogoutButtonTap() {
        launch {
            val result = logoutUseCase()
            result.success {
                view.navigateToLoginScreen()
            }
        }
   }
}
