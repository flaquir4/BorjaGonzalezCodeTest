package com.flaquir4.codetest.presentation

import android.os.Bundle
import com.flaquir4.codetest.R
import com.flaquir4.codetest.presentation.base.BaseActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginButton?.setOnClickListener {
           startActivity(MainActivity.getIntent(this))
        }
    }
}
