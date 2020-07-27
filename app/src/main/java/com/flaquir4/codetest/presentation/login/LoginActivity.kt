package com.flaquir4.codetest.presentation.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import com.flaquir4.codetest.R
import com.flaquir4.codetest.presentation.base.BaseActivity
import com.flaquir4.codetest.presentation.login.validator.LoginError
import com.flaquir4.codetest.presentation.main.MainActivity
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
            onLoginTap()
        }

        passwordEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                onLoginTap()
            }
            false
        }
    }

    private fun onLoginTap() {
        val username = usernameInputLayout?.editText?.text.toString()
        val password = passwordInputLayout?.editText?.text.toString()
        presenter.onLogInButtonTap(username, password)
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

    override fun handleFormErrors(formErrors: List<LoginError>) {
        passwordInputLayout?.error = null
        usernameInputLayout?.error = null

        formErrors.forEach { error ->
            when (error) {
                is LoginError.EmptyPassword ->
                    passwordInputLayout.error = getString(R.string.empty_password_error)

                is LoginError.EmptyEmail ->
                    usernameInputLayout.error = getString(R.string.empty_email_error)

                is LoginError.WrongEmailFormat ->
                    usernameInputLayout.error =
                        "${usernameInputLayout.error ?: ""}${getString(R.string.wrong_format_error)}"

                is LoginError.ShortPassword ->
                    passwordInputLayout.error =
                        "${passwordInputLayout.error ?: ""}${getString(R.string.short_password_error)}"
            }
        }

        passwordInputLayout?.editText?.addTextChangedListener {
            passwordInputLayout?.error = null
        }
        usernameInputLayout?.editText?.addTextChangedListener {
            usernameInputLayout?.error = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
