package com.flaquir4.codetest.presentation.login

import android.os.Bundle
import com.flaquir4.codetest.R
import com.flaquir4.codetest.presentation.MainActivity
import com.flaquir4.codetest.presentation.base.BaseActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity(), LoginView {
    @Inject
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        hookListeners()
    }

    private fun hookListeners() {
        loginButton?.setOnClickListener {
            val username = usernameInputLayout?.editText?.text.toString()
            val password = passwordInputLayout?.editText?.text.toString()

            presenter.onLogInButtonTap(username, password)
        }
    }

    override fun navigateToMainScreen() = startActivity(MainActivity.getIntent(this))

    override fun showBadCredentialsError() = showError(getString(R.string.login_error))

    override fun showRetryOption() = showError(getString(R.string.network_error))

    private fun showError(text: String) =
        Snackbar.make(container, text, Snackbar.LENGTH_LONG).show()

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
