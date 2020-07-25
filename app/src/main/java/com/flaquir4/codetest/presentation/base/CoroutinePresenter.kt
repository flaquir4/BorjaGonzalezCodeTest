package com.flaquir4.codetest.presentation.base

import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope

abstract class CoroutinePresenter : CoroutineScope by MainScope()
