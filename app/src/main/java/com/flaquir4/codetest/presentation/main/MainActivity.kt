package com.flaquir4.codetest.presentation.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.flaquir4.codetest.R
import com.flaquir4.codetest.presentation.base.BaseActivity
import com.flaquir4.codetest.presentation.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity(), MainView {

    companion object {
        fun getIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

    @Inject lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hookListeners()
    }

    private fun hookListeners() {
        logoutButton?.setOnClickListener {
            presenter.onLogoutButtonTap()
        }
    }

    override fun navigateToLoginScreen() {
        startActivity(LoginActivity.getIntent(this))
        finish()
    }
}
