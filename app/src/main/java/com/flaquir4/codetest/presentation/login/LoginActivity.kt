package com.flaquir4.codetest.presentation.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.flaquir4.codetest.R
import com.flaquir4.codetest.presentation.main.MainActivity
import com.flaquir4.codetest.presentation.base.BaseActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.loading.*
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity(), LoginView {

    companion object {
        fun getIntent(context: Context): Intent = Intent(context, LoginActivity::class.java)
    }

    @Inject
    lateinit var presenter: LoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.onStart()
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

    override fun navigateToMainScreen() {
        startActivity(MainActivity.getIntent(this))
        finish()
    }

    override fun showBadCredentialsError() = showError(getString(R.string.login_error))

    override fun showRetryOption() = showError(getString(R.string.network_error))

    private fun showError(text: String) =
        Snackbar.make(container, text, Snackbar.LENGTH_LONG).show()

    override fun showLoading() {
        loading?.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        loading?.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
